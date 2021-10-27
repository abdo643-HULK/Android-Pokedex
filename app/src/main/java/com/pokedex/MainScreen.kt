package com.pokedex

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pokedex.components.BottomNavBar
import com.pokedex.components.TopBar

@Composable
fun MainScreen(content: @Composable() () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController) },
    ) {
        content()
        //        Navigation(navController)
    }
}

@Composable
fun PreviewWithMainScreen(content: @Composable() () -> Unit) {
    MainScreen {
        content()
    }
}
