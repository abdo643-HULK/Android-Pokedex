package com.shehata.pokedex.ui.screens.pokemondetails

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shehata.pokedex.models.PokemonDetails
import com.shehata.pokedex.data.Network
import com.shehata.pokedex.data.favourites.FavouritePokemonsRepository
import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val favouritesRepository: FavouritePokemonsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableLiveData(PokemonDetailsState.Empty)

    val uiState: LiveData<PokemonDetailsState> = _uiState

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
            is PokemonDetailsActions.SetFavourite -> {
                _uiState.value?.pokemon?.let {
                    _uiState.value = _uiState.value?.copy(isFavourite = action.isFavourite)
                    val pokemon = FavouritePokemon(
                        id = it.id.toInt(),
                        name = it.name,
                        imageUrl = "${it.imageURL}",
                    )
                    viewModelScope.launch {
                        when (action.isFavourite) {
                            true -> {
                                favouritesRepository.add(pokemon)
                            }
                            false -> {
                                favouritesRepository.delete(pokemon)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPokemon(id: Int) {
        viewModelScope.launch {
            val isFavourite = favouritesRepository.getPokemon(id) != null
            val pokemon = Network.getPokemonDetails(id)
            _uiState.value?.let {
                _uiState.value = it.copy(pokemon = pokemon, isFavourite = isFavourite)
            }
        }
    }
}

/**
 * @param pokemon - it is a val because it is only one object and we only have to change it once
 */
data class PokemonDetailsState(
    var isFavourite: Boolean,
    var selectedStatType: StatTypes,
    var pokemon: PokemonDetails?,
//    val pokemonImagePainter: ImagePainter = ImageRequest(),
//    val evolutionsImagePainters: List<ImagePainter> = listOf(),
) {
    companion object {
        val Empty = PokemonDetailsState(
            pokemon = null,
            isFavourite = false,
            selectedStatType = StatTypes.Base
        )
    }
}

sealed class PokemonDetailsActions {
    data class GetPokemon(val id: Int) : PokemonDetailsActions()
    data class ChangeStat(val stat: StatTypes) : PokemonDetailsActions()
    data class SetFavourite(val isFavourite: Boolean) : PokemonDetailsActions()
}

enum class StatTypes {
    Base, Min, Max
}

