package com.shehata.pokedex.ui.screens.favourites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shehata.pokedex.data.favourites.FavouritePokemonsRepository
import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon
import com.shehata.pokedex.models.Pokemon
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val favouritePokemonsRepository: FavouritePokemonsRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(FavouritesState.Empty)

    val uiState: StateFlow<FavouritesState> = _uiState

//    val uiState: Flow<FavouritesState> = flow {
//        favouritePokemonsRepository.getAllPokemon
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = FavouritesState.Empty,
//    )

    init {
        viewModelScope.launch {
            favouritePokemonsRepository.getAllPokemon().collect { list ->
                val pokemons = list.map { Pokemon(it.id.toUInt(), it.name) }
                _uiState.value = FavouritesState(favourites = pokemons, isLoading = false)
            }
        }
    }
}

data class FavouritesState(
    val isLoading: Boolean,
    val favourites: List<Pokemon>
) {
    companion object {
        val Empty = FavouritesState(
            isLoading = true,
            favourites = emptyList(),
        )
    }
}
