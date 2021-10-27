package com.pokedex.screens

import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pokedex.PreviewWithMainScreen

@Composable
fun PokemonListScreen(navController: NavController) {
    Text(
        "PokemonList",
        fontSize = 30.sp
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    PreviewWithMainScreen {
        PokemonListScreen(
            rememberNavController()
        )
    }
}