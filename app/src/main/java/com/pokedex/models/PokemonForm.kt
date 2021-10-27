@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.pokedex.models

import java.net.URL
import java.util.Vector

data class PokemonForm(
        val id: UInt,
        val name: String,
        val imageURL: URL,
        val height: Float,
        val weight: Float,
        val types: Vector<String>,
        val abilities: Vector<Ability>,
        val baseStats: Stats,
        val formName: String,
)
