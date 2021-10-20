import com.apollographql.apollo.ApolloClient

import com.pokedex.models.PokemonDetails

object Network {
    val apollo =
        ApolloClient.builder().serverUrl("https://beta.pokeapi.co/graphql/v1beta").build()


    fun getPokemonDetails(id: Int): PokemonDetails {

        return PokemonDetails(1u, "HA")
    }

    fun load(id: UInt) {
    }
}







