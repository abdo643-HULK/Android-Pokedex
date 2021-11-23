package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.lifecycle.*
import androidx.compose.runtime.setValue
import coil.compose.ImagePainter
import coil.request.ImageRequest
import com.shehata.pokedex.models.PokemonDetails
import com.shehata.pokedex.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableLiveData(PokemonDetailsState())

    val uiState: LiveData<PokemonDetailsState>
        get() = _uiState

    init {
        savedStateHandle.get<Int>("pokemonId")?.let {
            actionsHandler(PokemonDetailsActions.GetPokemon(it))
        }
    }

    fun actionsHandler(action: PokemonDetailsActions) {
        when (action) {
            is PokemonDetailsActions.GetPokemon -> {
                    getPokemon(action.id)
            }
            is PokemonDetailsActions.ChangeStat -> {
                _uiState.value = _uiState.value?.copy(selectedStatType = action.stat)
            }
        }
    }

    private fun getPokemon(id: Int) {
        viewModelScope.launch {
            val pokemon = Network.getPokemonDetails(id)
            _uiState.value?.let {
                _uiState.value = it.copy(pokemon = pokemon)
            }
        }
    }
}

/**
 * @param pokemon - it is a val because it is only one object and we only have to change it once
 */
data class PokemonDetailsState(
//    var pokemon: PokemonDetails? = null,
    val pokemon: PokemonDetails? = null,
//    val pokemonImagePainter: ImagePainter = ImageRequest(),
//    val evolutionsImagePainters: List<ImagePainter> = listOf(),
    var selectedStatType: StatTypes = StatTypes.Base
)

sealed class PokemonDetailsActions {
    data class GetPokemon(val id: Int) : PokemonDetailsActions()
    data class ChangeStat(val stat: StatTypes) : PokemonDetailsActions()
}

enum class StatTypes {
    Base, Min, Max
}

