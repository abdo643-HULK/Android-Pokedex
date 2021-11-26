package com.shehata.pokedex.ui.screens.favourites

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon
import com.shehata.pokedex.models.Pokemon
import com.shehata.pokedex.ui.components.ScrollToTopButton
import com.shehata.pokedex.ui.screens.pokemonlist.PokemonListHeader
import com.shehata.pokedex.ui.screens.pokemonlist.PokemonListRow
import kotlinx.coroutines.launch

@Composable
fun Favourites(
    state: FavouritesState,
    openPokemonDetails: (pokemonId: UInt) -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    val pokemonsCellsPerRow by remember(orientation) {
        derivedStateOf {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        }
    }

    val listState = rememberLazyListState()
    val listSize = listState.layoutInfo.totalItemsCount
    val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0


    if (state.isLoading) {
        Text(
            "Loading",
            fontSize = 30.sp,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        PokemonList(
            pokemons = state.favourites,
            listState = listState,
            cellsPerRow = pokemonsCellsPerRow,
            navigateToDetailScreen = openPokemonDetails
        )
    }
}

@Composable
private fun PokemonList(
    pokemons: List<Pokemon>,
    listState: LazyListState,
    cellsPerRow: Int,
    navigateToDetailScreen: (id: UInt) -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cellSize = Dp((screenWidth / cellsPerRow - 30).toFloat())
    val coroutineScope = rememberCoroutineScope()

    Box(modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp)
        ) {
            items(
                pokemons.chunked(cellsPerRow),
                key = { it[0].id.toInt() }
            ) { pokemonRow ->
                PokemonListRow(
                    pokemonRow,
                    cellSize,
                    onPokemonSelect = navigateToDetailScreen
                )
            }
        }
    }

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        ScrollToTopButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            }
        )
    }
}
