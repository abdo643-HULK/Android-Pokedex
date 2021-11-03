package com.shehata.pokedex.screens.pokemonlist

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.shehata.pokedex.NavigationItem
import com.shehata.pokedex.models.Pokemon
import com.shehata.pokedex.models.PokemonGens
import com.shehata.pokedex.network.Network
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private val POKEMONS_PER_GEN = arrayOf(151f, 100f, 135f, 107f, 156f, 72f, 88f, 89f)

class PokemonListViewModel(val navController: NavController) :
    ViewModel() {
    private var genToFetch = 0
    private var fetchedGen = BooleanArray(POKEMONS_PER_GEN.size)
    private var _pokemons = MutableLiveData(mutableMapOf<String, List<Pokemon>>())
    private var _isFetching = false

    val pokemons: LiveData<MutableMap<String, List<Pokemon>>>
        get() = _pokemons
    var isFetching by mutableStateOf(false)
        private set

    init {
        fetchPokemon()
    }


    fun fetchPokemon() {
        val max = POKEMONS_PER_GEN.size - 1
        val con = if (genToFetch > max) max else genToFetch

        if (_isFetching || fetchedGen[con]) {
            return
        }

        fetchedGen[genToFetch] = true
        _isFetching = true
        isFetching = true

        viewModelScope.launch {
            val pokemon = Network.getPokemonByGeneration(PokemonGens[genToFetch]).sortedBy { it.id }
            _pokemons.value?.put(PokemonGens[genToFetch], pokemon)
            _pokemons.value = _pokemons.value
            ++genToFetch
            _isFetching = false
            isFetching = false
        }
    }

    fun navigateToPokemonDetails() {
        navController.navigate(NavigationItem.PokemonDetails.route)
    }
}