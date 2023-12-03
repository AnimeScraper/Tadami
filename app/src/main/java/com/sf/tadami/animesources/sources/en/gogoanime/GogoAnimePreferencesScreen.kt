package com.sf.tadami.animesources.sources.en.gogoanime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import com.sf.tadami.R
import com.sf.tadami.ui.components.data.Action
import com.sf.tadami.ui.tabs.settings.components.PreferenceScreen
import com.sf.tadami.ui.tabs.settings.model.DataStoreState
import com.sf.tadami.ui.tabs.settings.model.Preference
import com.sf.tadami.ui.tabs.settings.model.rememberDataStoreState

class GogoAnimePreferencesScreen(
    navController: NavHostController,
    dataStore: DataStore<Preferences>
) : PreferenceScreen {

    override val title: Int = R.string.sources_preferences_title

    @Composable
    override fun getTitle(): String {
        return stringResource(title,"GogoAnime")
    }

    override val backHandler: (() -> Unit) = {
        navController.navigateUp()
    }

    override val getCustomDataStore: (() -> DataStore<Preferences>) = {
        dataStore
    }

    override val topBarActions: List<Action> = listOf()

    @Composable
    override fun getPreferences(): List<Preference> {
        val gogoAnimePreferencesState = rememberDataStoreState(GogoAnimePreferences,getCustomDataStore())
        val gogoAnimePreferences by gogoAnimePreferencesState.value.collectAsState()

        return listOf(
            getNetworkGroup(prefState = gogoAnimePreferencesState, prefs = gogoAnimePreferences)
        )
    }

    @Composable
    fun getNetworkGroup(
        prefState: DataStoreState<GogoAnimePreferences>,
        prefs: GogoAnimePreferences
    ): Preference.PreferenceCategory {
        return Preference.PreferenceCategory(
            title = stringResource(id = R.string.category_network),
            preferenceItems = listOf(
                Preference.PreferenceItem.EditTextPreference(
                    value = prefs.baseUrl,
                    title = stringResource(id = R.string.sources_preferences_base_url),
                    subtitle = stringResource(id = R.string.sources_preferences_base_url_subtitle),
                    defaultValue = GogoAnimePreferences.DEFAULT_BASE_URL,
                    onValueChanged = {
                        prefState.setValue(
                            prefs.copy(
                                baseUrl = it
                            )
                        )
                        true
                    }
                )
            )
        )
    }
}