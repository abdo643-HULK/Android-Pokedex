package com.pokedex.models

data class Evolution(
    val name: String,
    val previousEvolution: UInt?,
    val trigger: Evolutiontrigger?,
    val minLvl: UInt?,
    val neededItem: String?,
    val neededTime: String?,
    val neededLocation: String?,
    val neededHappiness: UInt?,
    val neededAffection: UInt?,
    val needsRain: Boolean,
)
