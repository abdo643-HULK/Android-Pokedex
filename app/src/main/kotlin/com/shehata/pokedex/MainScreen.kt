package com.shehata.pokedex

import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.shehata.pokedex.components.BottomNavBar
import com.shehata.pokedex.components.Navigation
import com.shehata.pokedex.components.TopBar

@ExperimentalAnimationApi
@Composable
fun MainScreen(content: @Composable() () -> Unit) {
    val appState = rememberMyAppState()
    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(appState.navController) },
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        BoxWithConstraints(modifier = Modifier.padding(innerPadding)) {
//        content()
            Navigation(appState.navController, maxWidth = constraints.maxWidth)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun PreviewWithMainScreen(content: @Composable() () -> Unit) {
    MainScreen {
        content()
    }
}

class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val resources: Resources,
)

@ExperimentalAnimationApi
@Composable
fun rememberMyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController(),
    resources: Resources = LocalContext.current.resources,
) = remember(scaffoldState, navController, resources) {
    AppState(scaffoldState, navController, resources)
}