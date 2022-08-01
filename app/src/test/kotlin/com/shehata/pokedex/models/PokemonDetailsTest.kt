package com.shehata.pokedex.models

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class PokemonDetailsTest {
    lateinit var testPokemonDetails: PokemonDetails
    lateinit var testPokemonTypes: List<PokemonType>
    lateinit var testPokemonAbilities: List<Ability>
    lateinit var testPokemonBaseStats: Stats

    @Before
    fun setUp() {
        testPokemonTypes = listOf(PokemonType.from("normal"))
        testPokemonAbilities = listOf(Ability(slot = 1u, name = "Truant"))
        testPokemonBaseStats = Stats(
            hp = 100u,
            attack = 100u,
            defense = 100u,
            speed = 100u,
            specialAttack = 100u,
            specialDefense = 100u
        )

        testPokemonDetails = PokemonDetails(
            id = 1u,
            name = "Testmon",
            imageURL = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"),
            height = 10.0f,
            weight = 10.0f,
            types = testPokemonTypes,
            abilities = testPokemonAbilities,
            baseStats = testPokemonBaseStats,
            generation = null,
            isLegendary = null,
            isMythical = null,
            captureRate = null,
            isFormSwitchable = null,
            hasGenderDiff = null,
            genderRate = null,
            evolutions = listOf(),
            forms = listOf()
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getMaxStats() {
        val excpectedStats = Stats(hp=404u, attack=328u, defense=328u, speed=328u, specialAttack=328u, specialDefense=328u)
        val unexcpectedStats = Stats(hp=310u, attack=184u, defense=184u, speed=184u, specialAttack=184u, specialDefense=184u)

        assertEquals(testPokemonDetails.maxStats, excpectedStats)
        assertNotEquals(testPokemonDetails.maxStats, unexcpectedStats)
    }

    @Test
    fun getMinStats() {
        val expectedStats = Stats(hp=310u, attack=184u, defense=184u, speed=184u, specialAttack=184u, specialDefense=184u)
        val unexcpectedStats = Stats(hp=404u, attack=328u, defense=328u, speed=328u, specialAttack=328u, specialDefense=328u)

        assertEquals(testPokemonDetails.minStats, expectedStats)
        assertNotEquals(testPokemonDetails.minStats, unexcpectedStats)
    }
}