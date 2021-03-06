@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package com.shehata.pokedex.data

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.shehata.pokedex.GenerationQuery
import com.shehata.pokedex.PokemonDetailsQuery
import com.shehata.pokedex.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object Network {
    private val apollo: ApolloClient =
        ApolloClient.builder().serverUrl("https://beta.pokeapi.co/graphql/v1beta").build()

    private suspend fun <D : Operation.Data, T, V : Operation.Variables> runQuery(query: Query<D, T, V>): T? {
        val response = try {
            apollo.query(query).await()
        } catch (e: ApolloException) {
            Log.d("Apollo QueryError:", e.toString())
            null
        }

        return response?.data
    }

    suspend fun <T : String> getPokemonByGeneration(gen: T): List<Pokemon> {
        val generationList = runQuery(GenerationQuery(Input.fromNullable(gen)))?.pokemon

        return generationList?.map {
            Pokemon(
                it.id.toUInt(),
                it.name.replaceFirstChar(Char::titlecase)
            )
        } ?: listOf()
    }

    suspend fun getPokemonDetails(id: Int): PokemonDetails? {
        val uIntID = id.toUInt()
        val pokemon = withContext(Dispatchers.IO) {
            runQuery(PokemonDetailsQuery(id))?.pokemon
        }

        @Suppress("NAME_SHADOWING", "BlockingMethodInNonBlockingContext")
        return pokemon?.let { pokemon ->
            PokemonDetails(
                id = uIntID,
                name = pokemon.name,
                imageURL = URL(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                ),
                height = (pokemon.height)?.toFloat() ?: 0f / 10,
                weight = (pokemon.weight)?.toFloat() ?: 0f / 10,
                types = pokemon.typeDetails.map { PokemonType.from(it.types?.name ?: "") },
                abilities =
                pokemon.abilities.map {
                    Ability(slot = (it.slot).toUInt(), name = it.ability?.name ?: "")
                },
                baseStats =
                Stats(
                    hp =
                    pokemon.stats.find { it.stat?.name == "hp" }.let {
                        it?.base?.toUInt() ?: 0u
                    },
                    attack =
                    pokemon.stats.find { it.stat?.name == "attack" }.let {
                        it?.base?.toUInt() ?: 0u
                    },
                    defense =
                    pokemon.stats.find { it.stat?.name == "defense" }.let {
                        it?.base?.toUInt() ?: 0u
                    },
                    speed =
                    pokemon.stats.find { it.stat?.name == "speed" }.let {
                        it?.base?.toUInt() ?: 0u
                    },
                    specialAttack =
                    pokemon.stats
                        .find { it.stat?.name == "special-attack" }
                        .let { it?.base?.toUInt() ?: 0u },
                    specialDefense =
                    pokemon.stats
                        .find { it.stat?.name == "special-defense" }
                        .let { it?.base?.toUInt() ?: 0u },
                ),
                generation = pokemon.species?.generation?.name ?: "",
                isLegendary = pokemon.species?.isLegendary,
                isMythical = pokemon.species?.isMythical,
                captureRate = pokemon.species?.captureRate?.toUInt(),
                isFormSwitchable = pokemon.species?.isFormSwitchable,
                hasGenderDiff = pokemon.species?.hasGenderDiff,
                genderRate =
                pokemon.species?.genderRate?.let {
                    GenderRates(it)
                },
                evolutions = pokemon.species?.evolutions?.info?.map { ev ->
                    if (ev.evolutionDetails.isEmpty()) {
                        Evolution(
                            name = ev.name,
                            imageURL = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${ev.id}.png"),
                        )
                    } else {
                        val details = ev.evolutionDetails[0]
                        Evolution(
                            name = ev.name,
                            previousEvolutionId = ev.previousEvolution?.toUInt(),
                            imageURL = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${ev.id}.png"),
                            trigger = Evolutiontrigger.from(
                                details.trigger?.name ?: "unknown"
                            ),
                            minLvl = details.minLvl?.toUInt(),
                            neededItem = details.neededItem?.name,
                            neededTime = details.neededTime,
                            neededLocation = details.neededLocation?.name,
                            neededHappiness = details.neededHappiness?.toUInt(),
                            neededAffection = details.neededAffection?.toUInt(),
                            needsRain = details.needsRain
                        )
                    }
                } ?: listOf(),
                forms = pokemon.species?.pokeForms?.let { forms ->
                    forms.map { form ->
                        PokemonForm(
                            id = uIntID,
                            name = form.name,
                            imageURL = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${form.id}.png"),
                            height = (form.height?.toFloat() ?: 0f) / 10,
                            weight = (form.weight?.toFloat() ?: 0f) / 10,
                            types = form.typeDetails.map { it.types?.name ?: "" },
                            abilities = form.abilities.map {
                                Ability(
                                    slot = it.slot.toUInt(),
                                    name = it.ability?.name ?: ""
                                )
                            },
                            baseStats = Stats(
                                hp = form.stats.find { it.stat?.name == "hp" }.let {
                                    it?.base?.toUInt() ?: 0u
                                },
                                attack = form.stats.find { it.stat?.name == "attack" }.let {
                                    it?.base?.toUInt() ?: 0u
                                },
                                defense = form.stats.find { it.stat?.name == "defense" }.let {
                                    it?.base?.toUInt() ?: 0u
                                },
                                speed = form.stats.find { it.stat?.name == "speed" }.let {
                                    it?.base?.toUInt() ?: 0u
                                },
                                specialAttack = form.stats
                                    .find { it.stat?.name == "special-attack" }
                                    .let { it?.base?.toUInt() ?: 0u },
                                specialDefense = form.stats
                                    .find { it.stat?.name == "special-defense" }
                                    .let { it?.base?.toUInt() ?: 0u }
                            ),
                            formName = form.formDetails[0].formName
                        )
                    }
                } ?: listOf()
            )
        }
    }
}
