package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.*
import com.shehata.pokedex.network.Network
import kotlinx.coroutines.launch
import java.net.URL

@Preview(showBackground = true)
@Composable
fun PokemonDetailsScreenPreview() {
    val painter = painterResource(id = R.drawable.ic_pokeball_colored)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            PokemonBox(
                pokemon = dummyPokemon,
                painter
            )
        }
    }
}

@Composable
fun PokemonDetailsScreen(
    state: PokemonDetailsState,
    navigateUp: () -> Unit
) {
//    Column {
//        IconButton(
//            onClick = navigateUp
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.ArrowBack,
//                contentDescription = null,
//                modifier = Modifier
//                    .height(30.dp)
//            )
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            state.pokemon?.let {
                val painter = rememberImagePainter(
                    data = "${it.imageURL}",
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_pokeball_colored)
                    }
                )

                PokemonBox(
                    pokemon = it,
                    painter = painter
                )
            }
        }
    }
}

@Composable
fun PokemonBox(
    pokemon: PokemonDetails,
    painter: Painter,
    modifier: Modifier = Modifier
) {

    val boxSize = 450.dp
    Column(
        modifier
            .fillMaxWidth()
            .height(boxSize)
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        NameLabel(
            name = pokemon.name,
//            modifier = Modifier
//                .weight(1f)
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
        modifier = modifier
            .fillMaxWidth()
//            .height(40.dp)
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

private val dummyPokemon = PokemonDetails(
    id = 1u,
    name = "Balbasur",
    imageURL = URL(
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
    ),
    height = 0f,
    weight = 0f,
    types = arrayOf("fire").map { PokemonType.from(it) },
    abilities = listOf(Ability(slot = 1u, name = "Mango")),
    baseStats =
    Stats(
        hp = 0u,
        attack = 0u,
        defense = 0u,
        speed = 0u,
        specialAttack = 0u,
        specialDefense = 0u,
    ),
    generation = "kanto",
    isLegendary = false,
    isMythical = false,
    captureRate = 2u,
    isFormSwitchable = false,
    hasGenderDiff = false,
    genderRate = GenderRates(1),
    evolutions = listOf(),
    forms = listOf()
)