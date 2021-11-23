package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.Evolution

@Composable
fun EvolutionTree(
    evolutions: List<Evolution>,
    painters: List<ImagePainter>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        val evolutionMatrix = remember { getEvolutionMatrix(evolutions) }
        val max = remember { evolutionMatrix.maxByOrNull { it.size } }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            evolutionMatrix.forEachIndexed { i, evolution ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((130 * (max?.size ?: 1)).dp)
                        .weight(1f)
                ) {
                    evolution.forEachIndexed { j, evolution ->
                        val painter = painters[i + j]
                        Image(
                            painter = painter,
                            contentDescription = "Image of ${evolution.name}",
                            modifier = Modifier
                                .height(130.dp)
                                .fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}

private fun getEvolutionMatrix(evolutionList: List<Evolution>): List<List<Evolution>> {
    var list: MutableList<MutableList<Evolution>> = mutableListOf(mutableListOf())
    list[0] = mutableListOf(evolutionList[0]) // first pokemon is always 1

    var j = 0
    for (i in 1 until evolutionList.size) {
        if (evolutionList[i].previousEvolutionId != evolutionList[i - 1].previousEvolutionId) {
            list.add(mutableListOf())
            list[++j].add(evolutionList[i])
        } else {
            list[j].add(evolutionList[i])
        }
    }

    return list
}
