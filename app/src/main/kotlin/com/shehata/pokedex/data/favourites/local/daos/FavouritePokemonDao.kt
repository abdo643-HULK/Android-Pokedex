package com.shehata.pokedex.data.favourites.local.daos;

import androidx.room.*

import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon;
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritePokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<FavouritePokemon>>

    @Query("SELECT * FROM pokemon WHERE id IN (:pokemonIds)")
    suspend fun loadAllByIds(pokemonIds: IntArray): List<FavouritePokemon>

    @Query("SELECT * FROM pokemon WHERE id=:pokemonId")
    suspend fun getById(pokemonId: Int): FavouritePokemon

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: FavouritePokemon)

    @Insert
    suspend fun insertAll(vararg pokemons: FavouritePokemon)

    @Delete
    suspend fun delete(pokemon: FavouritePokemon)

}