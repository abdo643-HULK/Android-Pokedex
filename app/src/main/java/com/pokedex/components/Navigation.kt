package com.pokedex.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pokedex.NavigationItem
import com.pokedex.screens.PokemonListScreen
import com.pokedex.screens.ProfileScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.PokemonList.route) {
        composable(NavigationItem.PokemonList.route) {
            PokemonListScreen(navController = navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}