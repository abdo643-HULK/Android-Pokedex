package com.pokedex.models

data class Stats(
    val hp: UInt,
    val attack: UInt,
    val defense: UInt,
    val speed: UInt,
    val specialAttack: UInt,
    val specialDefense: UInt,
    ) {
    val biggestStat = arrayOf(hp, attack, defense, speed, specialDefense, specialAttack).maxOrNull() ?: attack + 1u
    val total =  hp + attack + defense + speed + specialAttack + specialDefense
}


