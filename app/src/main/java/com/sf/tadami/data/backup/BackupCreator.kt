package com.sf.tadami.data.backup

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hippo.unifile.UniFile
import com.sf.tadami.R
import com.sf.tadami.data.DataBaseHandler
import com.sf.tadami.data.backup.BackupCreateFlags.BACKUP_APP_PREFS
import com.sf.tadami.data.backup.BackupCreateFlags.BACKUP_EPISODE
import com.sf.tadami.data.backup.models.*
import com.sf.tadami.data.episode.EpisodeMapper
import com.sf.tadami.data.interactors.LibraryInteractor
import com.sf.tadami.domain.anime.LibraryAnime
import com.sf.tadami.notifications.backup.BackupFileValidator
import com.sf.tadami.ui.tabs.settings.model.CustomPreferences
import com.sf.tadami.ui.tabs.settings.screens.backup.BackupPreferences
import com.sf.tadami.ui.tabs.settings.screens.backup.BackupSerializer
import com.sf.tadami.utils.getDataStoreValues
import com.sf.tadami.utils.getPreferencesGroup
import com.sf.tadami.utils.hasPermission
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import okio.buffer
import okio.gzip
import okio.sink
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.io.FileOutputStream

class BackupCreator(
    private val context: Context,
) {

    private val handler: DataBaseHandler = Injekt.get()
    private val dataStore: DataStore<Preferences> = Injekt.get()
    private var backupPreferences: BackupPreferences = runBlocking {
        dataStore.getPreferencesGroup(BackupPreferences)
    }
    private val getLibary: LibraryInteractor = Injekt.get()


    @OptIn(ExperimentalSerializationApi::class)
    internal val parser = ProtoBuf

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun createBackup(uri: Uri, flags: Int, isAutoBackup: Boolean): String {
        if (!context.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            throw IllegalStateException(context.getString(R.string.missing_storage_permission))
        }
        val databaseAnime = getLibary.await()
        val backup = Backup(
            backupAnimes(databaseAnime, flags),
            backupAppPreferences(flags),
        )

        var file: UniFile? = null
        try {
            file = (
                    if (isAutoBackup) {
                        // Get dir of file and create
                        val dir = UniFile.fromUri(context, uri)

                        // Delete older backups
                        dir.listFiles { _, filename -> Backup.filenameRegex.matches(filename) }
                            .orEmpty()
                            .sortedByDescending { it.name }
                            .drop(backupPreferences.autoBackupMaxFiles - 1)
                            .forEach { it.delete() }

                        // Create new file to place backup
                        dir.createFile(Backup.getFilename())
                    } else {
                        UniFile.fromUri(context, uri)
                    }
                    )
                ?: throw Exception(context.getString(R.string.create_backup_file_error))

            if (!file.isFile) {
                throw IllegalStateException("Failed to get handle on a backup file")
            }

            val byteArray = parser.encodeToByteArray(BackupSerializer, backup)
            if (byteArray.isEmpty()) {
                throw IllegalStateException(context.getString(R.string.empty_backup_error))
            }

            file.openOutputStream().also {
                // Force overwrite old file
                (it as? FileOutputStream)?.channel?.truncate(0)
            }.sink().gzip().buffer().use { it.write(byteArray) }
            val fileUri = file.uri

            // Make sure it's a valid backup file
            BackupFileValidator().validate(context, fileUri)

            return fileUri.toString()
        } catch (e: Exception) {
            Log.e("BackupCreator", e.stackTraceToString())
            file?.delete()
            throw e
        }
    }

    private suspend fun backupAnimes(animes: List<LibraryAnime>, flags: Int): List<BackupAnime> {
        return animes.map {
            backupAnime(it, flags)
        }
    }

    private suspend fun backupAnime(anime: LibraryAnime, options: Int): BackupAnime {
        // Entry for this anime
        val animeObject = BackupAnime.copyFrom(anime)

        // Check if user wants episode information in backup
        if (options and BACKUP_EPISODE == BACKUP_EPISODE) {
            // Backup all the episodes
            handler.awaitList {
                episodeQueries.getEpisodesByAnimeId(
                    animeId = anime.id,
                    mapper = EpisodeMapper::mapBackupEpisode,

                    )
            }
                .takeUnless(List<BackupEpisode>::isEmpty)
                ?.let { animeObject.episodes = it }
        }

        return animeObject
    }

    private suspend fun backupAppPreferences(flags: Int): List<BackupPreference> {
        if (flags and BACKUP_APP_PREFS != BACKUP_APP_PREFS) return emptyList()
        return dataStore.getDataStoreValues().asMap().toBackupPreferences()
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<Preferences.Key<*>, Any>.toBackupPreferences(): List<BackupPreference> {
        return this.filterKeys {
            !CustomPreferences.isPrivate(it.name) && !CustomPreferences.isAppState(it.name)
        }
            .mapNotNull { (key, value) ->
                when (value) {
                    is Int -> BackupPreference(key.name, IntPreferenceValue(value))
                    is Long -> BackupPreference(key.name, LongPreferenceValue(value))
                    is Float -> BackupPreference(key.name, FloatPreferenceValue(value))
                    is String -> BackupPreference(key.name, StringPreferenceValue(value))
                    is Boolean -> BackupPreference(key.name, BooleanPreferenceValue(value))
                    is Set<*> -> (value as? Set<String>)?.let {
                        BackupPreference(key.name, StringSetPreferenceValue(it))
                    }

                    else -> null
                }
            }
    }
}