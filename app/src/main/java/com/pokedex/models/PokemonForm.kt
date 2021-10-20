package com.pokedex.models
import java.util.Vector
import java.net.URL

data class PokemonForm (
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