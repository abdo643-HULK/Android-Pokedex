package com.shehata.pokedex.screens.pokemonlist

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pokedex.PreviewWithMainScreen
import com.pokedex.components.ScrollToTopButton
import com.pokedex.models.Pokemon
import com.pokedex.models.PokemonGens
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    navigateToDetailScreen: (pokemonId: UInt) -> Unit,
    modifier: Modifier = Modifier
) {
    val orientation = LocalConfiguration.current.orientation
    val pokemonsCellsPerRow = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
    val isFetching = viewModel.isFetching
    val pokemonsList by viewModel.pokemons.observeAsState(mutableMapOf())

    PokemonList(
        pokemons = pokemonsList,
        cellsPerRow = pokemonsCellsPerRow,
        isFetchingNextGen = isFetching,
        modifier = modifier,
        navigateToDetailScreen = navigateToDetailScreen,
        onPagination = {
            viewModel.fetchPokemon()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun PokemonList(
    pokemons: Map<String, List<Pokemon>>,
    cellsPerRow: Int,
    isFetchingNextGen: Boolean,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (id: UInt) -> Unit,
    onPagination: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cellSize = Dp((screenWidth / cellsPerRow - 30).toFloat())

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val finished = remember(isFetchingNextGen) {
        derivedStateOf {
            !isFetchingNextGen
        }
    }

    val listSize = listState.layoutInfo.totalItemsCount
    val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0



    Box(modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            pokemons.values.forEachIndexed { sectionIndex, pokemons ->
                val currentGen = sectionIndex + 1

                stickyHeader {
                    PokemonListHeader(currentGen)
                }

                items(pokemons.chunked(cellsPerRow)) { pokemonRow ->
                    PokemonListRow(
                        pokemonRow,
                        cellSize,
                        onPokemonSelect = navigateToDetailScreen
                    )
                }
            }
        }



        if (finished.value && listSize != 0 && lastVisibleItemIndex / listSize.toFloat() > 0.9f) {
            onPagination()
        }

        AnimatedVisibility(
            visible = listState.firstVisibleItemIndex > 0,
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

//@Preview(showBackground = true)
//@Composable
//fun PokemonListScreenPreview() {
//    PreviewWithMainScreen {
//        PokemonListScreen(
//            rememberNavController()
//        )
//    }
//}