@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.shehata.pokedex.models

import java.net.URL

data class Evolution(
    val name: String,
    val imageURL: URL,
    val previousEvolutionId: UInt? = null,
    val trigger: Evolutiontrigger? = null,
    val minLvl: UInt? = null,
    val neededItem: String? = null,
    val neededTime: String? = null,
    val neededLocation: String? = null,
    val neededHappiness: UInt? = null,
    val neededAffection: UInt? = null,
    val needsRain: Boolean = false,
)
