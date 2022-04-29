package com.shehata.pokedex.screens.pokemondetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.*
import java.net.URL


@Composable
fun PokemonDetailsScreen(
    state: PokemonDetailsState,
    actions: (PokemonDetailsActions) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        state.pokemon?.let { pokemon ->
            PokemonDetail(state = state, pokemon = pokemon, actions = actions)
        }
    }
}

@Composable
private fun PokemonDetail(
    state: PokemonDetailsState,
    pokemon: PokemonDetails,
    actions: (PokemonDetailsActions) -> Unit
) {
    val stats by remember(state.selectedStatType) {
        derivedStateOf {
            when (state.selectedStatType) {
                StatTypes.Base -> pokemon.baseStats
                StatTypes.Min -> pokemon.minStats
                StatTypes.Max -> pokemon.maxStats
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            val painter = rememberImagePainter(
                data = "${pokemon.imageURL}",
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_pokeball_colored)
                }
            )

            PokemonBox(
                pokemon = pokemon,
                painter = painter
            )
        }

        item {
            val evolutionsPainters = pokemon.evolutions.map {
                rememberImagePainter(
                    data = "${it.imageURL}",
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_pokeball_colored)
                    }
                )
            }

            EvolutionTree(
                pokemon.evolutions,
                painters = evolutionsPainters
            )
        }

        item {
            StatsBox(
                stats = stats,
                color = pokemon.types[0].color ?: MaterialTheme.colors.background,
                selected = state.selectedStatType,
                onStatChange = { statType ->
                    actions(PokemonDetailsActions.ChangeStat(statType))
                }
            )
        }
    }

}

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

private val dummyPokemon = PokemonDetails(
    id = 1u,
    name = "Balbasur",
    imageURL = URL(
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
    ),
    height = 0f,
    weight = 0f,
    types = arrayOf("grass", "poison").map { PokemonType.from(it) },
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