package com.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Color.Black,
    primaryVariant = Purple500,
    secondary = Teal200,
    background = LightBG,
    surface = LightBG,
    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = DarkGB,
    surface = DarkGB,
)


@Composable
fun PokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
//    val colors by if (darkTheme) LoadDarkColors() else LoadLightColors()

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


@Composable
fun LoadDarkColors(): State<Colors> {
    val DarkColorPalette = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = DarkGB
    )

    return rememberUpdatedState(newValue = DarkColorPalette)
}

@Composable
fun LoadLightColors(): State<Colors> {
    val DarkColorPalette = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = DarkGB
    )

    return rememberUpdatedState(newValue = DarkColorPalette)
}