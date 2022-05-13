package com.shehata.pokedex.data.favourites

import com.shehata.pokedex.data.favourites.local.daos.FavouritePokemonDao
import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon

class FavouritePokemonsRepository(private val favouritePokemonDao: FavouritePokemonDao) {
    fun getAllPokemon() = favouritePokemonDao.getAll()

    suspend fun getPokemon(id: Int) = favouritePokemonDao.getById(id)

    suspend fun add(pokemon: FavouritePokemon) = favouritePokemonDao.insert(pokemon)

    suspend fun delete(pokemon: FavouritePokemon) = favouritePokemonDao.delete(pokemon)

}