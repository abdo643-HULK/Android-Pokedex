@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.pokedex.PokemonDetailsQuery
import com.pokedex.models.Ability
import com.pokedex.models.GenderRates
import com.pokedex.models.PokemonDetails
import com.pokedex.models.Stats
import java.net.URL

object Network {
    val apollo = ApolloClient.builder().serverUrl("https://beta.pokeapi.co/graphql/v1beta").build()

    suspend fun getPokemonDetails(id: Int): PokemonDetails? {

        val response =
                try {
                    apollo.query(PokemonDetailsQuery(id)).await()
                } catch (e: ApolloException) {
                    Log.d("PokemonDetails", e.toString())
                    null
                }

        val pokemon = response?.data?.pokemon

        return pokemon?.let { pokemon ->
            PokemonDetails(
                    id = id.toUInt(),
                    name = pokemon.name,
                    imageURL =
                            URL(
                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                            ),
                    height = (pokemon.height)?.toFloat() ?: 0f / 10,
                    weight = (pokemon.weight)?.toFloat() ?: 0f / 10,
                    types = pokemon.typeDetails.map { it.types?.name ?: "" },
                    abilities =
                            pokemon.abilities.map {
                                Ability(slot = (it.slot).toUInt(), name = it.ability?.name ?: "")
                            },
                    baseStats =
                            Stats(
                                    // pokemon.stats.find { it.stat?.name == "hp" }?.let {  }
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
                    captureRate = pokemon.species?.captureRate?.let { it.toUInt() },
                    // isFormSwitchable= pokemon.species?.isFormSwitchable,
                    // hasGenderDiff= pokemon.species?.hasGenderDiff,
                    genderRate =
                            pokemon.species?.genderRate?.let {
                                GenderRates(it)
                            },
                    evoltutions = pokemon.species?.evolutions?.info.map { ev ->
                        if ev.evolutionDetails.size > 0 {
                            val details = ev.evolutionDetails[0]
                            Evolution(
                                name: ev.name,
                                previousEvolution: ev.previousEvolution != nil ? UInt16(ev.previousEvolution!) : nil,
                                trigger: Evolutiontrigger(rawValue: details.trigger?.name ?: "unknown"),
                                minLvl: details.minLvl != nil ? UInt8(details.minLvl!) : nil,
                                neededItem: details.neededItem?.name,
                                neededTime: details.neededTime,
                                neededLocation: details.neededLocation?.name,
                                neededHappiness: details.neededHappiness != nil ? UInt16(details.neededHappiness!) : nil,
                                neededAffection: details.neededAffection != nil ? UInt16(details.neededAffection!) : nil,
                                needsRain: details.needsRain
                            )
                        }
                        else null
                    } ?: [],
            )
        }
    }

    fun load(id: UInt) {}
}
