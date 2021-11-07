package com.shehata.pokedex.screens.pokemonlist

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shehata.pokedex.NavigationItem
import com.shehata.pokedex.models.Pokemon
import com.shehata.pokedex.models.PokemonGens
import com.shehata.pokedex.network.Network
import kotlinx.coroutines.launch
import java.lang.Exception

private val POKEMONS_PER_GEN = arrayOf(151f, 100f, 135f, 107f, 156f, 72f, 88f, 89f)

class PokemonListViewModel(
    val savedStateHandle: SavedStateHandle,
) :
    ViewModel() {
    private var genToFetch = 0
    private var fetchedGen = BooleanArray(POKEMONS_PER_GEN.size)
    private var _uiState = MutableLiveData(PokemonListState())

//    val uiState = mutableStateOf(PokemonListState())

//    val n = LiveData<Pager(PagingConfig(
//        pageSize = POKEMONS_PER_GEN[0].toInt()
//    )) {
//        fetchPokemon()
//    }>

    val uiState: LiveData<PokemonListState>
        get() = _uiState

    init {
        fetchPokemon()
    }

    fun fetchPokemon() {
        val max = POKEMONS_PER_GEN.size - 1
        val con = if (genToFetch > max) max else genToFetch


        if (fetchedGen[con]) return

//        Log.i("POKEMON", "Fetching: ${genToFetch}")

        fetchedGen[genToFetch] = true

        _uiState.value?.isFetching = true
        _uiState.value = _uiState.value

        viewModelScope.launch {
            val pokemon = Network.getPokemonByGeneration(PokemonGens[genToFetch]).sortedBy { it.id }
            _uiState.value?.pokemons?.put(PokemonGens[genToFetch], pokemon)
            _uiState.value?.isFetching = false
            _uiState.value = _uiState.value
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
