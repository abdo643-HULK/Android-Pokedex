@file:OptIn(ExperimentalAnimationApi::class)

package com.shehata.pokedex.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.shehata.pokedex.BottomNavigationItem
import com.shehata.pokedex.NavigationItem
import com.shehata.pokedex.screens.pokemondetails.PokemonDetailsScreen
import com.shehata.pokedex.screens.pokemondetails.PokemonDetailsViewModel
import com.shehata.pokedex.screens.pokemonlist.PokemonListScreen
import com.shehata.pokedex.screens.pokemonlist.PokemonListViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    maxWidth: Int,
    start: String = BottomNavigationItem.PokemonList.route
) {
    AnimatedNavHost(navController, start) {
        addPokemonListScreen(
            navController,
            maxWidth / 2
        )
        addPokemonDetailsScreen(
            navController,
            maxWidth / 2
        )
    }
}

fun NavGraphBuilder.addPokemonListScreen(
    navController: NavController,
    width: Int
) {
    composable(
        BottomNavigationItem.PokemonList.route,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -(width / 2) },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                NavigationItem.PokemonDetails.route ->
                    slideInHorizontally(
                        initialOffsetX = { width / 2 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                else -> null
            }
        },
        popEnterTransition = { initial, _ ->
            slideInHorizontally(
                initialOffsetX = { -(width / 2) },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel = PokemonListViewModel(navController)
        PokemonListScreen(
            viewModel,
            navigateToDetailScreen = { pokemonId ->
                navController.navigate("${NavigationItem.PokemonDetails.route}/$pokemonId")
            }
        )
    }
}

fun NavGraphBuilder.addPokemonDetailsScreen(
    navController: NavController,
    width: Int,
) {
    composable(
        route = NavigationItem.PokemonDetails.route + "/{pokemonId}",
        arguments = NavigationItem.PokemonDetails.arguments,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { width / 2 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                BottomNavigationItem.PokemonList.route ->
                    slideOutHorizontally(
                        targetOffsetX = { -(width / 2) },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                else -> null
            }
        },
        popExitTransition = { _, target ->
            slideOutHorizontally(
                targetOffsetX = { width / 2 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val vm: PokemonDetailsViewModel = viewModel()
        val state by vm.uiState.observeAsState()

        state?.let {
            PokemonDetailsScreen(it)
        }
    }
}