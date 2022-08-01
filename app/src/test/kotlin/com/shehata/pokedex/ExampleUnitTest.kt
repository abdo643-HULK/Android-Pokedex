package com.shehata.pokedex

import com.shehata.pokedex.models.Stats
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var stats: Stats

    @Before
    fun setUp() {
        stats = Stats(hp = 1u, attack = 1u, defense = 1u, speed = 1u, specialAttack = 2u, specialDefense = 1u)
    }

    @Test
    fun testStatsTotal() {
        assertEquals(stats.total, 7)
        assertNotEquals(stats.total, 8)
    }

    @Test
    fun testStatsBiggest() {
        assertEquals(stats.biggestStat, 2)
        assertNotEquals(stats.biggestStat, 3)
    }
}