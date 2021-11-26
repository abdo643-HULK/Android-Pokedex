@file:OptIn(ExperimentalAnimationApi::class)

package com.shehata.pokedex.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.shehata.pokedex.navigation.addTopLevelFavourites
import com.shehata.pokedex.navigation.addTopLevelPokemonList
import com.shehata.pokedex.navigation.addTopLevelProfile

sealed class Screen(val route: String) {
    object PokemonList : Screen("pokemon-list")
    object Favourites : Screen("favourites")
    object Profile : Screen("profile")
}

sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object PokemonList : LeafScreen("pokemon-list")
    object Favourites : LeafScreen("favourites")
    object Profile : LeafScreen("profile")

    object PokemonDetails : LeafScreen("pokemon/{pokemonId}") {
        fun createRoute(root: Screen, pokemonId: UInt): String {
            return "${root.route}/pokemon/$pokemonId"
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    maxWidth: Int,
    start: String = Screen.PokemonList.route
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = start
    ) {
        val width = maxWidth / 2
        addTopLevelPokemonList(navController, width)
        addTopLevelFavourites(navController, width)
        addTopLevelProfile(navController, width)
    }
}

