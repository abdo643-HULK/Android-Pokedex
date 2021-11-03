package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.lifecycle.*
import com.pokedex.models.PokemonDetails
import com.pokedex.network.Network
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState = MutableLiveData(PokemonDetailsState(null))

    val uiState: LiveData<PokemonDetailsState>
        get() = _uiState

    init {

        savedStateHandle.get<Int>("pokemonId")?.let { pokemonId ->
            viewModelScope.launch {
                val pokemon = Network.getPokemonDetails(pokemonId)
                _uiState.value?.let {
                    _uiState.value = it.copy(pokemon = pokemon)
                }
            }
        }
    }
}

data class PokemonDetailsState(
    val pokemon: PokemonDetails? = null
)