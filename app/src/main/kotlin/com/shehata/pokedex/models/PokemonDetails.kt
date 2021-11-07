@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.shehata.pokedex.models

import java.net.URL

data class PokemonDetails(
        val id: UInt,
        val name: String,
        val imageURL: URL,
        val height: Float,
        val weight: Float,
        val types: List<PokemonType>,
        val abilities: List<Ability>,
        val baseStats: Stats,
        val generation: String?,
        val isLegendary: Boolean?,
        val isMythical: Boolean?,
        val captureRate: UInt?,
        val isFormSwitchable: Boolean?,
        val hasGenderDiff: Boolean?,
        val genderRate: GenderRates?,
        val evolutions: List<Evolution?>,
        val forms: List<PokemonForm>
) {}