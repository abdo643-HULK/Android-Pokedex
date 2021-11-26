package com.shehata.pokedex

import android.app.Application
import com.shehata.pokedex.data.favourites.local.databases.FavouritesDatabase
import com.shehata.pokedex.data.favourites.FavouritePokemonsRepository

class PokedexApplication : Application() {
    private val favouritesDatabase by lazy { FavouritesDatabase.getDatabase(this) }
    val favouritePokemonsRepository by lazy { FavouritePokemonsRepository(favouritesDatabase.pokemonDao()) }
}