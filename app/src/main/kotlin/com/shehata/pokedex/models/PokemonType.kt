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
//            return when (this) {
//                Normal -> Color(0xA8A77A)
//                Fire -> Color(0xEE8130)
//                Water -> Color(0x6390F0)
//                Electric -> Color(0xF7D02C)
//                Grass -> Color(0x7AC74C)
//                Ice -> Color(0x96D9D6)
//                Fighting -> Color(0xC22E28)
//                Poison -> Color(0xA33EA1)
//                Ground -> Color(0xE2BF65)
//                Flying -> Color(0xA98FF3)
//                Psychic -> Color(0xF95587)
//                Bug -> Color(0xA6B91A)
//                Rock -> Color(0xB6A136)
//                Ghost -> Color(0x735797)
//                Dragon -> Color(0x6F35FC)
//                Dark -> Color(0x705746)
//                Steel -> Color(0xB7B7CE)
//                Fairy -> Color(0xD685AD)
//                Shadow -> Color(0x000000)
//                Unknown -> null
//            }
        }

    companion object {
        fun from(rawValue: String): PokemonType = values().first { it.type == rawValue }
    }
}



// enum class PokeType(val type: String) {
//     Normal("normal"),
//     Fire("fire"),
//     Water("water"),
//     Electric("electric"),
//     Grass("grass"),
//     Ice("ice"),
//     Fighting("fighting"),
//     Poison("poison"),
//     Ground("ground"),
//     Flying("flying"),
//     Psychic("psychic"),
//     Bug("bug"),
//     Rock("rock"),
//     Ghost("ghost"),
//     Dragon("dragon"),
//     Dark("dark"),
//     Steel("steel"),
//     Fairy("fairy"),
//     Shadow("shadow"),
//     Unkown("unknown");


//     var color: String = ""
//         get() {
//             return when (this) {
//                 Normal -> "#A8A77A"
//                 Fire -> "#EE8130"
//                 Water -> "#6390F0"
//                 Electric -> "#F7D02C"
//                 Grass -> "#7AC74C"
//                 Ice -> "#96D9D6"
//                 Fighting -> "#C22E28"
//                 Poison -> "#A33EA1"
//                 Ground -> "#E2BF65"
//                 Flying -> "#A98FF3"
//                 Psychic -> "#F95587"
//                 Bug -> "#A6B91A"
//                 Rock -> "#B6A136"
//                 Ghost -> "#735797"
//                 Dragon -> "#6F35FC"
//                 Dark -> "#705746"
//                 Steel -> "#B7B7CE"
//                 Fairy -> "#D685AD"
//                 Shadow -> "#000000"
//                 Unkown -> "#ffffff"
//             }
//         }


//     companion object {
//         fun from(rawValue: String): PokeType = PokeType.values().first { it.type == rawValue }
//     }
// }