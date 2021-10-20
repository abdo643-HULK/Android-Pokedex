package com.pokedex.models

data class Stats(
        val hp: UInt,
        val attack: UInt,
        val defense: UInt,
        val speed: UInt,
        val specialAttack: UInt,
        val specialDefense: UInt,

        public lateinit var biggestStat: UInt = {
            [hp, attack, defense, speed, specialDefense, specialAttack].maxOrNull() ?? attack + 1
        }
    
        public lateinit var total: UInt = {
            hp + attack + defense + speed + specialAttack + specialDefense
        }()
)


