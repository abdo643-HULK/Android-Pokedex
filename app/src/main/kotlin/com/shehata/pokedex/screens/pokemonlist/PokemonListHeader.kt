package com.shehata.pokedex.screens.pokemonlist

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pokedex.extensions.drawColoredShadow

private const val cornerRadius = 10
private const val lightShadowRadius = 6.25f
private const val darkShadowRadius = 5.5f

@Composable
fun PokemonListHeader(generation: Int) {
    Spacer(modifier = Modifier.height(3.dp))
    ShadowSurface {
        Box(
            modifier = Modifier
                .clipToBounds(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Generation $generation",
                fontSize = 35.sp,
                lineHeight = 1.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.padding(5.dp))
}

@Composable
private fun ShadowSurface(
    isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val roundedCornerRadius = RoundedCornerShape(cornerRadius)
//    val (shadow) =
    val shadow = remember(isDarkTheme) {
        derivedStateOf {
            if (isDarkTheme) Modifier
                .drawColoredShadow(
                    color = Color.DarkGray.copy(0.05f),
                    shadowRadius = darkShadowRadius.dp,
                    borderRadius = 10.dp,
                    offsetX = (-0.05).dp,
                    offsetY = (-0.05).dp
                )
                .drawColoredShadow(
                    color = Color.DarkGray.copy(0.5f),
                    shadowRadius = darkShadowRadius.dp,
                    borderRadius = cornerRadius.dp,
                    offsetX = 5.dp,
                    offsetY = 5.dp
                ) else Modifier
                .drawColoredShadow(
                    color = Color.White.copy(0.7f),
                    shadowRadius = lightShadowRadius.dp,
                    borderRadius = cornerRadius.dp,
                    offsetX = (-7).dp,
                    offsetY = (-1).dp
                )
                .drawColoredShadow(
                    color = Color.Black.copy(0.2f),
                    shadowRadius = lightShadowRadius.dp,
                    borderRadius = 10.dp,
                    offsetX = 5.dp,
                    offsetY = 5.dp
                )
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .then(shadow.value),
        shape = roundedCornerRadius,
        elevation = 0.dp,
        color = MaterialTheme.colors.surface
    ) {
        content()
    }
}
