package com.sf.tadami.ui.components.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.sf.tadami.domain.anime.Anime
import com.sf.tadami.ui.utils.rememberLazyGridState

@Composable
fun InfiniteAnimeGrid(
    modifier: Modifier = Modifier,
    animeList: LazyPagingItems<Anime>,
    onAnimeClicked: (anime: Anime) -> Unit,
    onAnimeLongClicked: (anime: Anime) -> Unit = onAnimeClicked,
    snackbarHostState : SnackbarHostState = remember { SnackbarHostState() },
    contentPadding : PaddingValues = PaddingValues(0.dp),
) {

    val lazyGridState = animeList.rememberLazyGridState()

    AnimeGrid(
        modifier = modifier,
        contentPadding = contentPadding,
        animeList = animeList,
        onAnimeClicked = onAnimeClicked,
        onAnimeLongClicked = onAnimeLongClicked,
        snackbarHostState = snackbarHostState,
        lazyGridState = lazyGridState
    )
}