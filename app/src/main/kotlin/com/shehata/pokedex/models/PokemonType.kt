package com.shehata.pokedex.models

import androidx.compose.ui.graphics.Color
import com.shehata.pokedex.extensions.parseHexString

enum class PokemonType(val type: String) {
    Normal("normal"),
    Fire("fire"),
    Water("water"),
    Electric("electric"),
    Grass("grass"),
    Ice("ice"),
    Fighting("fighting"),
    Poison("poison"),
    Ground("ground"),
    Flying("flying"),
    Psychic("psychic"),
    Bug("bug"),
    Rock("rock"),
    Ghost("ghost"),
    Dragon("dragon"),
    Dark("dark"),
    Steel("steel"),
    Fairy("fairy"),
    Shadow("shadow"),
    Unknown("unknown");

    var color: Color? = null
        private set
        get() {
            return when (this) {
                Normal -> Color.parseHexString("#A8A77A")
                Fire -> Color.parseHexString("#EE8130")
                Water -> Color.parseHexString("#6390F0")
                Electric -> Color.parseHexString("#F7D02C")
                Grass -> Color.parseHexString("#7AC74C")
                Ice -> Color.parseHexString("#96D9D6")
                Fighting -> Color.parseHexString("#C22E28")
                Poison -> Color.parseHexString("#A33EA1")
                Ground -> Color.parseHexString("#E2BF65")
                Flying -> Color.parseHexString("#A98FF3")
                Psychic -> Color.parseHexString("#F95587")
                Bug -> Color.parseHexString("#A6B91A")
                Rock -> Color.parseHexString("#B6A136")
                Ghost -> Color.parseHexString("#735797")
                Dragon -> Color.parseHexString("#6F35FC")
                Dark -> Color.parseHexString("#705746")
                Steel -> Color.parseHexString("#B7B7CE")
                Fairy -> Color.parseHexString("#D685AD")
                Shadow -> Color.parseHexString("#000000")
                Unknown -> null
            }
        }

    companion object {
        fun from(rawValue: String): PokemonType = values().first { it.type == rawValue }
    }
}