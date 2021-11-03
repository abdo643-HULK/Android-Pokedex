package com.shehata.pokedex.screens.pokemonlist

import android.content.res.Configuration
import android.util.Log
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.extensions.drawColoredShadow
import com.shehata.pokedex.models.Pokemon
import java.net.URL

private const val cornerRadius = 12
private const val lightShadowRadius = 6.25f
private const val darkShadowRadius = 5.5f

@Composable
fun PokemonListCell(
    pokemon: Pokemon,
    size: Dp,
    onPokemonSelect: (id: UInt) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_pokeball_colored
) {
    val cellState = rememberCellState(imageURL = pokemon.imageURL, placeholder = placeholder)

    ShadowSurface(
        size = size,
        shadow = cellState.shadow,
        modifier = Modifier.clickable {
            onPokemonSelect(pokemon.id)
        }
    ) {
        Column(
            modifier = modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            IDLabel(pokemon.id)
            PokemonImage(cellState.imagePainter, size - 55.dp, pokemon.name)
            NameLabel(pokemon.name)
        }
    }
}

@Composable
fun IDLabel(id: UInt, modifier: Modifier = Modifier) {
    BasicText(
        text = "$id",
        maxLines = 1,
        style = TextStyle(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
    )
}


@Composable
fun PokemonImage(
    painter: ImagePainter,
    size: Dp,
    pokemonName: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = "Official Artwork of $pokemonName ",
        modifier = modifier
            .height(size)
            .offset(y = (-5).dp)
            .fillMaxWidth()
    )
}

@Composable
fun NameLabel(
    name: String,
    modifier: Modifier = Modifier
) {
    BasicText(
        text = name,
        maxLines = 1,
        style = TextStyle(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .offset(y = (-3).dp)
            .fillMaxWidth()
    )
}


@Composable
private fun ShadowSurface(
    size: Dp,
    shadow: Modifier,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val roundedCornerRadius = RoundedCornerShape(cornerRadius)
    Surface(
        modifier = Modifier
            .size(size)
            .then(shadow),
        shape = roundedCornerRadius,
        color = MaterialTheme.colors.surface,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            content()
        }
    }
}

@Composable
private fun rememberCellState(
    imageURL: URL,
    @DrawableRes placeholder: Int,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    imagePainter: ImagePainter = rememberImagePainter(
        data = "$imageURL",
        builder = {
            crossfade(true)
            placeholder(placeholder)
        }
    ),
) = remember(imagePainter) {
    PokemonCellState(imagePainter, isDarkTheme)
}

private class PokemonCellState(
    val imagePainter: ImagePainter,
    val isDarkTheme: Boolean,
) {
    private val darkModeShadow = Modifier
        .drawColoredShadow(
            color = Color.DarkGray.copy(0.05f),
            shadowRadius = darkShadowRadius.dp,
            borderRadius = cornerRadius.dp,
            offsetX = (-0.01).dp,
            offsetY = (-0.01).dp
        )
        .drawColoredShadow(
            color = Color.DarkGray.copy(0.5f),
            shadowRadius = darkShadowRadius.dp,
            borderRadius = cornerRadius.dp,
            offsetX = 5.dp,
            offsetY = 5.dp
        )
    private val lightModeShadow = Modifier
        .drawColoredShadow(
            color = Color.Black.copy(0.6f),
            shadowRadius = lightShadowRadius.dp,
            borderRadius = cornerRadius.dp,
            offsetX = 8.dp,
            offsetY = 8.dp
        )
        .drawColoredShadow(
            color = Color.White.copy(0.7f),
            shadowRadius = lightShadowRadius.dp,
            borderRadius = (cornerRadius).dp,
            offsetX = (-7).dp,
            offsetY = (-7).dp
        )

    private var _shadow by mutableStateOf(if (isDarkTheme) darkModeShadow else lightModeShadow)
    val shadow
        get() = if (isDarkTheme) darkModeShadow else lightModeShadow
//    val shadow = mutableStateOf(if (isDarkTheme) darkModeShadow else lightModeShadow)
}