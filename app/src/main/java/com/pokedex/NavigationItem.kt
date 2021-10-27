package com.pokedex

import androidx.annotation.DrawableRes

sealed class NavigationItem(var route: String, @DrawableRes var icon: Int, var title: String) {
    object PokemonList : NavigationItem("pokemon-list", R.drawable.ic_pokeball, "Pokemon")
    object Profile : NavigationItem("profile", R.drawable.ic_outline_person_24, "Profile")
}
