package com.shehata.pokedex.extensions

import androidx.compose.ui.graphics.Color

val Color.Transparent: Color
    get() = Color.Black.copy(0f)


fun Color.Companion.parseHexString(color: String): Color? {
    return try {
        val color = android.graphics.Color.parseColor(color)
        Color(color)
    } catch (e: RuntimeException) {
        null
    }
}
