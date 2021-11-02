@file:OptIn(ExperimentalAnimationApi::class)
package com.pokedex.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.pokedex.BottomNavigationItem
import com.pokedex.NavigationItem
import com.pokedex.screens.pokemondetails.PokemonDetailsScreen
import com.pokedex.screens.pokemondetails.PokemonDetailsViewModel
import com.pokedex.screens.pokemonlist.PokemonListScreen
import com.pokedex.screens.pokemonlist.PokemonListViewModel

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
//        composable(NavigationItem.PokemonList.route) {
//            val viewModel = PokemonListViewModel(navController)
//            PokemonListScreen(viewModel)
//        }
//        composable(NavigationItem.Profile.route) {
//            ProfileScreen(navController = navController)
//        }
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
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = { initial, _ ->
            slideInHorizontally(
                initialOffsetX = { -width },
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
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = { _, target ->
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ){
        val viewModel: PokemonDetailsViewModel = PokemonDetailsViewModel()
        PokemonDetailsScreen()
    }
}