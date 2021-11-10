package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.Evolution

@Composable
fun EvolutionTree(evolutions: List<Evolution?>) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.height(100.dp).fillMaxWidth()
        ) {
            evolutions.forEach { ev ->
                ev?.let { evolution ->
                    val (
                        name,
                        imageURL,
                        previousEvolutionId,
                        trigger,
                        minLvl,
                        neededItem,
                        neededTime,
                        neededLocation,
                        neededHappiness,
                        neededAffection,
                        needsRain
                    ) = evolution

                    val painter = rememberImagePainter(
                        data = "${imageURL}",
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_pokeball_colored)
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                    )
//                    Text(
//                        text = "$name ,$previousEvolutionId , ${minLvl}"
//                    )
                }
            }
        }
    }
}

