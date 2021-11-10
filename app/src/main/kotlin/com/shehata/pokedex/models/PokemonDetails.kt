@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.shehata.pokedex.models

import java.net.URL
import kotlin.math.floor

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
) {

    val maxStats by lazy {
        val maxHp = if (id == 292u) 1u else calcMaxHP()
        val maxAttack = calcMaxStat(baseStats.attack)
        val maxDefense = calcMaxStat(baseStats.defense)
        val maxSpeed = calcMaxStat(baseStats.speed)
        val maxSpecialAttack = calcMaxStat(baseStats.specialAttack)
        val maxSpecialDefense = calcMaxStat(baseStats.specialDefense)

        Stats(
            hp = maxHp,
            attack = maxAttack,
            defense = maxDefense,
            speed = maxSpeed,
            specialAttack = maxSpecialAttack,
            specialDefense = maxSpecialDefense
        )
    }

    val minStats by lazy {
        val minHp = if (id == 292u) 1u else calcMinHP()
        val minAttack = calcMinStat(baseStats.attack)
        val minDefense = calcMinStat(baseStats.defense)
        val minSpeed = calcMinStat(baseStats.speed)
        val minSpecialAttack = calcMinStat(baseStats.specialAttack)
        val minSpecialDefense = calcMinStat(baseStats.specialDefense)

        Stats(
            hp = minHp,
            attack = minAttack,
            defense = minDefense,
            speed = minSpeed,
            specialAttack = minSpecialAttack,
            specialDefense = minSpecialDefense
        )
    }

    private fun calcMaxHP(): UInt {
        val s = 2u * baseStats.hp + 94u
        val n = floor(s.toDouble() + 110.0)
        return n.toUInt()
    }

    private fun calcMinHP(): UInt {
        val s = 2u * baseStats.hp
        val n: Double = floor(s.toDouble() + 110.0)
        return n.toUInt()
    }

    private fun calcMaxStat(stat: UInt): UInt {
        val newBase = 2u * stat + 94u + 5u
        val res = floor(newBase.toDouble() * 1.1)
        return res.toUInt()
    }

    private fun calcMinStat(stat: UInt): UInt {
        val baseDouble = 2u * stat
        val firstFloor = floor(baseDouble.toDouble() + 5.0)
        val res = floor(firstFloor * 0.9)
        return res.toUInt()
    }
}
