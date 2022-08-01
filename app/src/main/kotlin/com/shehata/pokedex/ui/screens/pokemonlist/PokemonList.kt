@file:OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)

package com.shehata.pokedex.ui.screens.pokemonlist

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shehata.pokedex.ui.components.ScrollToTopButton
import com.shehata.pokedex.models.Pokemon
import kotlinx.coroutines.launch


//@Composable
//fun PokemonListRoute(
//    viewModel: PokemonListViewModel,
//) {
//    val uiState by viewModel.uiState.observeAsState(PokemonListState())
//    val pokemonsList = uiState.pokemons
//    val isFetching = uiState.isFetching
//}

@Composable
fun PokemonListScreen(
    state: PokemonListState,
    fetchPokemon: () -> Unit,
    navigateToDetailScreen: (pokemonId: UInt) -> Unit,
    modifier: Modifier = Modifier
) {
    val orientation = LocalConfiguration.current.orientation

    val listState = rememberLazyListState()
    val listSize by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
    val lastVisibleItemIndex by remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
    }
    val pokemonsCellsPerRow by remember(orientation) {
        derivedStateOf { if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3 }
    }
    val fetchMore by remember(listSize, lastVisibleItemIndex) {
        derivedStateOf { lastVisibleItemIndex / listSize.toFloat() > 0.9f }
    }

    LaunchedEffect(fetchMore) {
        if (!state.isFetching && listSize != 0 && fetchMore) {
            fetchPokemon()
        }
    }

    PokemonList(
        pokemons = state.pokemons,
        listState = listState,
        cellsPerRow = pokemonsCellsPerRow,
        modifier = modifier,
        navigateToDetailScreen = navigateToDetailScreen,
    )
}

@Composable
fun PokemonList(
    pokemons: Map<String, List<Pokemon>>,
    listState: LazyListState,
    cellsPerRow: Int,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (id: UInt) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val coroutineScope = rememberCoroutineScope()
    val cellSize by remember {
        derivedStateOf { Dp((screenWidth / cellsPerRow - 30).toFloat()) }
    }

    Box(modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp)
        ) {
            pokemons.values.forEachIndexed { sectionIndex, pokemons ->
                val currentGen = sectionIndex + 1

                stickyHeader {
                    PokemonListHeader(currentGen)
                }

                items(
                    pokemons.chunked(cellsPerRow),
                    key = {
                        it[0].id.toInt()
                    }
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
}

@Composable
fun PokemonListRow(
    pokemons: List<Pokemon>,
    cellSize: Dp,
    onPokemonSelect: (id: UInt) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (pokemon in pokemons) {
            PokemonListCell(
                pokemon = pokemon,
                size = cellSize,
                onPokemonSelect
            )
        }
    }
}