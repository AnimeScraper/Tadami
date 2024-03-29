package com.sf.tadami.data.sources

import com.sf.tadami.data.DataBaseHandler
import com.sf.tadami.network.api.online.AnimeCatalogueSource
import com.sf.tadami.ui.tabs.animesources.AnimeSourcesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SourceRepository {
    fun getSourcesWithNonLibraryAnime(): Flow<List<SourceWithCount>>

    suspend fun deleteAnimesNotInLibraryBySourceIds(sourceIds : List<String>)
}

class SourceRepositoryImpl(
    private val sourcesManager: AnimeSourcesManager,
    private val handler: DataBaseHandler
) : SourceRepository
{
    override fun getSourcesWithNonLibraryAnime(): Flow<List<SourceWithCount>> {
        val sourceIdWithNonLibraryAnime = handler.subscribeToList { animeQueries.getSourceIdsWithNonLibraryAnime() }
        return sourceIdWithNonLibraryAnime.map { sourceId ->
            sourceId.map { (sourceId, count) ->
                val source = sourcesManager.getExtensionById(sourceId)
                SourceWithCount(source, count)
            }
        }
    }

    override suspend fun deleteAnimesNotInLibraryBySourceIds(sourceIds: List<String>) {
        handler.await {
            animeQueries.deleteAnimesNotInLibraryBySourceIds(sourceIds)
        }
    }
}



data class SourceWithCount(
    val source: AnimeCatalogueSource,
    val count: Long,
) {

    val id: String
        get() = source.id

    val name: String
        get() = source.name
}
