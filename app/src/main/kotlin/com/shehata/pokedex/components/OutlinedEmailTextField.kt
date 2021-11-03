package com.shehata.pokedex.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun OutlinedEmailTextField(
    email: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        label = {
            Text(text = "Email")
        },
        onValueChange = onValueChange
    )
}
