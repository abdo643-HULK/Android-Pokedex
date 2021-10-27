package com.pokedex.models

data class GenderRates(val rate: Int)  {
    val rates =
            mapOf<Int, Map<String, Float>>(
                    0 to mapOf("male" to 100f, "female" to 0f),
                    1 to mapOf("male" to 87.5f, "female" to 12.5f),
                    2 to mapOf("male" to 75f, "female" to 25f),
                    3 to mapOf("male" to 62.5f, "female" to 37.5f),
                    4 to mapOf("male" to 50f, "female" to 50f),
                    5 to mapOf("male" to 37.5f, "female" to 62.5f),
                    6 to mapOf("male" to 25f, "female" to 75f),
                    7 to mapOf("male" to 12.5f, "female" to 87.5f),
                    8 to mapOf("male" to 0f, "female" to 100f),
            )

    var male: Float = 0f
    var female: Float = 0f
    var isGenderLess: Boolean = false

    init {
        if (rate == 0) isGenderLess = true
        else {
            val result = rates.get(rate)
            male = result?.get("male") ?: 0f
            female = result?.get("female") ?: 0f
        }
    }
}
