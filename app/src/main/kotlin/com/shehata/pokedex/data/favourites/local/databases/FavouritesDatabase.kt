package com.shehata.pokedex.data.favourites.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shehata.pokedex.data.favourites.local.daos.FavouritePokemonDao
import com.shehata.pokedex.data.favourites.local.entities.FavouritePokemon


@Database(entities = [FavouritePokemon::class], version = 1)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract fun pokemonDao(): FavouritePokemonDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FavouritesDatabase? = null

        fun getDatabase(context: Context): FavouritesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouritesDatabase::class.java,
                    "favourites-database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}