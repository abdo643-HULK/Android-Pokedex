package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.pokedex.R
import com.pokedex.models.PokemonType
import com.pokedex.models.PokemonDetails

@Composable
fun PokemonDetailsScreen(
    state: PokemonDetailsState
) {

    state?.pokemon?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
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
    val boxSize = 400.dp

    Column(
        modifier
            .fillMaxWidth()
            .height(boxSize),
        verticalArrangement = Arrangement.Center,
    ) {
        NameLabel(name = pokemon.name)
        PokemonImage(
            painter = painter,
            size = boxSize - 55.dp,
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
    BasicText(
        text = name.replaceFirstChar(Char::titlecase),
        maxLines = 1,
        style = TextStyle(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
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
            .fillMaxWidth()
    )
}

@Composable
fun TypesRow(
    types: List<PokemonType>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        for (type in types) {
            Log.i("POKEMON", type.name)
            Log.i("POKEMON", type.color.toString())
            BasicText(
                text = type.name,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    background = type.color ?: MaterialTheme.colors.background,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

