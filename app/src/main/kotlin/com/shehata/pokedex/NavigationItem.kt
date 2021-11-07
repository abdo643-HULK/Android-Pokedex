package com.shehata.pokedex

import androidx.annotation.DrawableRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class BottomNavigationItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
) {
    object PokemonList : BottomNavigationItem("pokemon-list", "Pokemon", R.drawable.ic_pokeball)
    object Profile : BottomNavigationItem("profile", "Profile", R.drawable.ic_outline_person_24)
    object Favourites :
        BottomNavigationItem("favourites", "Favourites", R.drawable.ic_baseline_favorite_border_24)
}

sealed class NavigationItem(val route: String, val arguments: List<NamedNavArgument>) {
    object PokemonList : BottomNavigationItem("pokemon-list", "Pokemon", R.drawable.ic_pokeball)
    object Profile : BottomNavigationItem("profile", "Profile", R.drawable.ic_outline_person_24)
    object Favourites :
        BottomNavigationItem("favourites", "Favourites", R.drawable.ic_baseline_favorite_border_24)
    object PokemonDetails :
        NavigationItem(
            route = "pokemon-details",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        )
}