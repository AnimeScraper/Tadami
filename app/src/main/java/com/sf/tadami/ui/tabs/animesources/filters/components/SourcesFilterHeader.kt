package com.sf.tadami.ui.tabs.animesources.filters.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sf.tadami.ui.tabs.settings.widget.TogglePreference
import com.sf.tadami.utils.Lang

@Composable
fun SourcesFilterHeader(
    language: String,
    enabled: Boolean,
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TogglePreference(
        modifier = modifier,
        title = stringResource(id = Lang.getLangByName(language)!!.getRes()),
        checked = enabled,
        onCheckedChanged = { onClickItem(language) },
    )
}