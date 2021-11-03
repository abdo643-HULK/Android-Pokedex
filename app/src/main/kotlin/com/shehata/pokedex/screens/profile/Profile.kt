@file:OptIn(ExperimentalAnimationApi::class)
package com.shehata.pokedex.screens.profile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shehata.pokedex.PreviewWithMainScreen

@Composable
fun ProfileScreen(navController: NavController) {
    Text(
        "Profile",
        fontSize = 30.sp
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PreviewWithMainScreen {
        ProfileScreen(
            rememberNavController()
        )
    }
}