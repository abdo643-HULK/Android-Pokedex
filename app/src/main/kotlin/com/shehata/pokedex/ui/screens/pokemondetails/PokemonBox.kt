package com.shehata.pokedex.ui.screens.pokemondetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shehata.pokedex.extensions.drawColoredShadow
import com.shehata.pokedex.models.PokemonDetails
import com.shehata.pokedex.models.PokemonType


private const val CORNER_RADIUS = 20
private const val LIGHT_MODE_SHADOW_RADIUS = 6
private const val DARK_MODE_SHADOW_RADIUS = 6

@Composable
fun PokemonBox(
    pokemon: PokemonDetails,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    val boxSize = 410.dp
    PokemonBoxShadow(
        isDarkTheme = isSystemInDarkTheme(),
        cornerRadius = 6,
        modifier = Modifier.padding(20.dp)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .height(boxSize)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            NameLabel(
                name = pokemon.name,
//                modifier = Modifier.weight(1f)
            )
            PokemonImage(
                painter = painter,
                pokemonName = pokemon.name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(10.dp)
            )
            TypesRow(
                types = pokemon.types,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun NameLabel(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name.replaceFirstChar(Char::titlecase),
        maxLines = 1,
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h2,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun PokemonImage(
    painter: Painter,
    pokemonName: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = "Official Artwork of $pokemonName ",
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun TypesRow(
    types: List<PokemonType>,
    modifier: Modifier = Modifier
) {
    val roundedCornerRadius = RoundedCornerShape(30)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(40.dp)
            .clip(roundedCornerRadius)
            .fillMaxWidth()
    ) {
        for (type in types) {
            val backgroundColor = type.color ?: MaterialTheme.colors.background
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .clipToBounds()
                    .weight(1f)
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                BasicText(
                    text = type.name,
                    maxLines = 1,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Composable
fun PokemonBoxShadow(
    isDarkTheme: Boolean,
    cornerRadius: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val roundedCornerRadius = RoundedCornerShape(cornerRadius)

    val darkModeShadow = remember {
        Modifier
            .drawColoredShadow(
                color = Color.DarkGray.copy(0.05f),
                shadowRadius = DARK_MODE_SHADOW_RADIUS.dp,
                borderRadius = CORNER_RADIUS.dp,
                offsetX = (-0.05).dp,
                offsetY = (-0.05).dp
            )
            .drawColoredShadow(
                color = Color.DarkGray.copy(0.6f),
                shadowRadius = DARK_MODE_SHADOW_RADIUS.dp,
                borderRadius = CORNER_RADIUS.dp,
                offsetX = 5.dp,
                offsetY = 5.dp
            )
    }

    val lightModeShadow = remember {
        Modifier
            .drawColoredShadow(
                color = Color.Black.copy(0.6f),
                shadowRadius = LIGHT_MODE_SHADOW_RADIUS.dp,
                borderRadius = CORNER_RADIUS.dp,
                offsetX = 8.dp,
                offsetY = 8.dp
            )
            .drawColoredShadow(
                color = Color.White.copy(0.7f),
                shadowRadius = LIGHT_MODE_SHADOW_RADIUS.dp,
                borderRadius = (CORNER_RADIUS).dp,
                offsetX = (-7).dp,
                offsetY = (-7).dp
            )
    }

    val shadow by remember(isDarkTheme) {
        derivedStateOf {
            if (isDarkTheme) darkModeShadow else lightModeShadow
        }
    }

    Surface(
        modifier = modifier.then(shadow),
        shape = roundedCornerRadius,
    ) {
        content()
    }
}

