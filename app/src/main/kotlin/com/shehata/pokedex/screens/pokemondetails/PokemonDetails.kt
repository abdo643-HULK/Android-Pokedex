package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.PokemonType
import com.shehata.pokedex.models.PokemonDetails

@Composable
fun PokemonDetailsScreen(
    state: PokemonDetailsState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            state.pokemon?.let {
                PokemonBox(
                    pokemon = it
                )
            }
        }

    }
}

@Composable
fun PokemonBox(
    pokemon: PokemonDetails,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(
        data = "${pokemon.imageURL}",
        builder = {
            crossfade(true)
            placeholder(R.drawable.ic_pokeball_colored)
        }
    )
    val boxSize = 450.dp

    Column(
        modifier
            .fillMaxWidth()
            .height(boxSize)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        NameLabel(name = pokemon.name)
        PokemonImage(
            painter = painter,
            size = boxSize - 150.dp,
            pokemonName = pokemon.name
        )
        TypesRow(
            types = pokemon.types
        )
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
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
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
            .fillMaxWidth()
    )
}

@Composable
fun TypesRow(
    types: List<PokemonType>,
    modifier: Modifier = Modifier
) {
    val roundedCornerRadius = RoundedCornerShape(20)

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

