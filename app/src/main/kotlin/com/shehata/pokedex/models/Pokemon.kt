package com.shehata.pokedex.models

import java.net.URL

data class Pokemon(
    val id: UInt,
    val name: String
) {
    val imageURL = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png")
}
