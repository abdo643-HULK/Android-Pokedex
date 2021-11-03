package com.shehata.pokedex.models

enum class Evolutiontrigger(val value: String) {
    LEVEL_UP("level-up"),
    TRADE("trade"),
    ITEM_USE ( "use-item"),
    SHED("shed"),
    OTHER("other"),
    UNKNOWN("unknown");

    companion object {
        fun from(rawValue: String): Evolutiontrigger = Evolutiontrigger.values().first { it.value == rawValue }
    }
}
