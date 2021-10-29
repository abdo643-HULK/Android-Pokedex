package com.pokedex.screens.PokemonList

import androidx.lifecycle.ViewModel
import com.pokedex.models.Pokemon
import com.pokedex.models.PokemonGenerations
import com.pokedex.network.Network

class PokemonListViewModel : ViewModel() {
    var pokemons = listOf<Pokemon>()
        private set
}