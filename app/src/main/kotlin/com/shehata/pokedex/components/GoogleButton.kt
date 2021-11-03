package com.shehata.pokedex.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.shehata.pokedex.R

@Composable
fun GoogleButton() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            tint = Color.Unspecified,
            painter = painterResource(id = R.drawable.googleg_standard_color_18),
            contentDescription = null,
        )
        Text(
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            text = "Google"
        )
        Icon(
            tint = Color.Transparent,
            imageVector = Icons.Default.MailOutline,
            contentDescription = null,
        )
    }
}