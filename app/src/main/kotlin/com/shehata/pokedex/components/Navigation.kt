@file:OptIn(ExperimentalAnimationApi::class)

package com.shehata.pokedex.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.shehata.pokedex.BottomNavigationItem
import com.shehata.pokedex.NavigationItem
import com.shehata.pokedex.screens.pokemondetails.PokemonDetailsScreen
import com.shehata.pokedex.screens.pokemondetails.PokemonDetailsState
import com.shehata.pokedex.screens.pokemondetails.PokemonDetailsViewModel
import com.shehata.pokedex.screens.pokemonlist.PokemonListScreen
import com.shehata.pokedex.screens.pokemonlist.PokemonListState
import com.shehata.pokedex.screens.pokemonlist.PokemonListViewModel
import com.shehata.pokedex.screens.profile.ProfileScreen

@Composable
fun Navigation(
    navController: NavHostController,
    maxWidth: Int,
    start: String = BottomNavigationItem.PokemonList.route
) {

    AnimatedNavHost(navController, start) {
        val width = maxWidth / 2
        addPokemonListScreen(
            navController,
            width
        )
        addPokemonDetailsScreen(
            navController,
            width
        )
        addFavouritesScreen(
            navController,
            width
        )
        addProfileScreen(
            navController,
            width
        )
    }
}

fun NavGraphBuilder.addPokemonListScreen(
    navController: NavController,
    width: Int
) {
    composable(
        BottomNavigationItem.PokemonList.route,
        enterTransition = {
            when (initialState.destination.route) {
                NavigationItem.PokemonDetails.route -> {
                    Log.i("POKEMON", "${true}")
                    slideInHorizontally(
                        initialOffsetX = { width },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route?.contains(NavigationItem.PokemonDetails.route)) {
                true -> {
                    slideInHorizontally(
                        initialOffsetX = { -width },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }

        },
        exitTransition = {
            when (targetState.destination.route?.contains(NavigationItem.PokemonDetails.route)) {
                true -> {
                    slideOutHorizontally(
                        targetOffsetX = { -width },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                }
                else -> null
            }
        },
    ) {
        val vm: PokemonListViewModel = viewModel()
        val uiState by vm.uiState.observeAsState(PokemonListState())

        PokemonListScreen(
            uiState,
            fetchPokemon = vm::fetchPokemon,
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
        enterTransition = {
            when (initialState.destination.route) {
                BottomNavigationItem.PokemonList.route -> {
                    slideInHorizontally(
                        initialOffsetX = { width },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                BottomNavigationItem.PokemonList.route ->
                    slideOutHorizontally(
                        targetOffsetX = { -width },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                else -> null
            }
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val vm: PokemonDetailsViewModel = viewModel()
        val state by vm.uiState.observeAsState(PokemonDetailsState(null))

        PokemonDetailsScreen(
            state,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

fun NavGraphBuilder.addFavouritesScreen(
    navController: NavController,
    width: Int,
) {
    composable(
        route = BottomNavigationItem.Favourites.route,
    ) {
        Text(
            "Favourites",
            modifier = Modifier.fillMaxSize()
        )
    }
}


fun NavGraphBuilder.addProfileScreen(
    navController: NavController,
    width: Int,
) {
    composable(
        route = BottomNavigationItem.Profile.route,
    ) {
        ProfileScreen(navController = navController)
    }
}

