package com.shehata.pokedex.ui.screens.pokemondetails

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.shehata.pokedex.models.PokemonDetails
import com.shehata.pokedex.data.Network
import com.shehata.pokedex.data.favourites.FavouritePokemonsRepository
import com.shehata.pokedex.ui.screens.favourites.FavouritesViewModel
import kotlinx.coroutines.launch

class PokemonDetailsViewModelFactory constructor(
    private val repository: FavouritePokemonsRepository,
    owner: SavedStateRegistryOwner,
    args: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, args) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
            return PokemonDetailsViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}