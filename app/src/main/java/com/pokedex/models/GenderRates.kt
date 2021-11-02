package com.pokedex.models

//private const val MALE = 0
//private const val FEMALE = 1

data class GenderRates(val rate: Int) {
    // for better optimization the map could
    // be changed to an array where 0 is male and 1 is female
    private val rates = arrayOf(
        mapOf("male" to 100f, "female" to 0f),
        mapOf("male" to 87.5f, "female" to 12.5f),
        mapOf("male" to 75f, "female" to 25f),
        mapOf("male" to 62.5f, "female" to 37.5f),
        mapOf("male" to 50f, "female" to 50f),
        mapOf("male" to 37.5f, "female" to 62.5f),
        mapOf("male" to 25f, "female" to 75f),
        mapOf("male" to 12.5f, "female" to 87.5f),
        mapOf("male" to 0f, "female" to 100f),
    )

    val male by lazy {
        if (rate == -1) return@lazy 0f
        rates[rate]["male"] ?: 0f
//        rates[rate][MALE] ?: 0f
    }

    val female by lazy {
        if (rate == -1) return@lazy 0f
        rates[rate]["female"] ?: 0f
//        rates[rate][FEMALE] ?: 0f
    }

    val isGenderLess by lazy {
        if (rate != -1) return@lazy false
        true
    }

}
