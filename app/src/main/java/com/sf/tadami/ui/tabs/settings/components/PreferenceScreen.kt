package com.sf.tadami.ui.tabs.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sf.tadami.R
import com.sf.tadami.ui.components.data.Action
import com.sf.tadami.ui.components.topappbar.ActionItem
import com.sf.tadami.ui.tabs.settings.model.Preference

interface PreferenceScreen {

    @get:StringRes
    val title: Int

    @Composable
    fun getTitle(): String {
        return stringResource(title)
    }

    @Composable
    fun getPreferences() : List<Preference>

    val backHandler: (() -> Unit)?

    val getCustomDataStore: (() -> DataStore<Preferences>)?
        get() = null

    val topBarActions: List<Action>
        get() = emptyList()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = getTitle()) },
                    navigationIcon = {
                        if (backHandler != null) {
                            IconButton(onClick = backHandler!!) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = stringResource(R.string.stub_text),
                                )
                            }
                        }
                    },
                    actions = {
                        topBarActions.forEach {
                            ActionItem(action = it)
                        }
                    },
                )
            },
            content = { contentPadding ->
                PreferenceParser(
                    modifier = Modifier.padding(contentPadding),
                    items = getPreferences()
                )
            },
        )
    }
}