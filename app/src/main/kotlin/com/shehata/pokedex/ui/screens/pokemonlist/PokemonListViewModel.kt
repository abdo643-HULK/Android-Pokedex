package com.shehata.pokedex.ui.screens.pokemonlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shehata.pokedex.data.Network
import com.shehata.pokedex.models.Pokemon
import com.shehata.pokedex.models.PokemonGens
import kotlinx.coroutines.launch

private val POKEMONS_PER_GEN = arrayOf(151f, 100f, 135f, 107f, 156f, 72f, 88f, 89f)

class PokemonListViewModel(
    val savedStateHandle: SavedStateHandle,
) :
    ViewModel() {
    private var genToFetch = 0
    private var fetchedGen = BooleanArray(POKEMONS_PER_GEN.size)

    var uiState by mutableStateOf(PokemonListState())
        private set

    init {
        fetchPokemon()
    }

    fun fetchPokemon() {
        val max = POKEMONS_PER_GEN.size - 1
        val con = if (genToFetch > max) max else genToFetch

        if (fetchedGen[con]) return

        fetchedGen[genToFetch] = true
        uiState.isFetching = true

        viewModelScope.launch {
            val pokemon = Network.getPokemonByGeneration(PokemonGens[genToFetch]).sortedBy { it.id }
            uiState.isFetching = false
            uiState.pokemons[PokemonGens[genToFetch]] = pokemon
            ++genToFetch
        }
    }
}

data class PokemonListState(
    var pokemons: MutableMap<String, List<Pokemon>> = mutableMapOf(),
    var isFetching: Boolean = false
)

//class PokemonListSource() : PagingSource<Int, List<Pokemon>>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
//        TODO("Not yet implemented")
//
//        return try {
//            val nextGen = params.key ?: 0
//            val pokemons = Network.getPokemonByGeneration(PokemonGens[nextGen])
//
//            LoadResult.Page<Int, List<Pokemon>>(
//                data = pokemons,
//                prevKey = if (nextGen == 0) null else nextGen,
//                nextKey = nextGen + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, List<Pokemon>>): Int? {
//        TODO("Not yet implemented")
//    }
//
//}
