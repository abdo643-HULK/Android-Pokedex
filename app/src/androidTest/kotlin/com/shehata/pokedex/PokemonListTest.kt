package com.shehata.pokedex

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.shehata.pokedex.ui.screens.MainScreen
import com.shehata.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class PokemonListTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
    @Test
    fun myTest() {
        composeTestRule.setContent {
            PokedexTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }

        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithText("Charmander")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("Charmander").performClick()
    }
}