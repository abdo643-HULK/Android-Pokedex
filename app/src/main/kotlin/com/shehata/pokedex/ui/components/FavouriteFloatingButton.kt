package com.shehata.pokedex.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.shehata.pokedex.R

@Composable
fun FavouriteFloatingButton(
    isFavourite: Boolean,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = backgroundColor,
        modifier = modifier,
    ) {
        Icon(
            imageVector = when {
                isFavourite -> Icons.Default.Favorite
                else -> Icons.Default.FavoriteBorder
            },
            contentDescription = when {
                isFavourite -> stringResource(R.string.favourite_pokemon_add)
                else -> stringResource(R.string.favourite_pokemon_remove)
            }
        )
    }
}