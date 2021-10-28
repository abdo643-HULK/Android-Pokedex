@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.pokedex.models

import java.net.URL

data class PokemonForm(
    val id: UInt,
    val name: String,
    val imageURL: URL,
    val height: Float,
    val weight: Float,
    val types: List<String>,
    val abilities: List<Ability>,
    val baseStats: Stats,
    val formName: String,
)
