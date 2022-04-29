package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = Modifier.fillMaxWidth()
    ) {
        val evolutionMatrix = getEvolutionMatrix(evolutions)

        Row(
            Modifier.fillMaxWidth()
        ) {
            evolutionMatrix.forEachIndexed { i, evolution ->
                val painter = painters[i]
                if (evolution.size > 1) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        evolution.forEach {
                            Image(
                                painter = painter,
                                contentDescription = "Image of ${it.name}",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(100.dp)
                            )
                        }
                    }
                } else {
                    Image(
                        painter = painter,
                        contentDescription = "Image of ${evolution[0].name}",
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                    )
                }

            }
        }
    }
}

private fun getEvolutionMatrix(evolutionList: List<Evolution>): List<List<Evolution>> {
    val list: MutableList<MutableList<Evolution>> = mutableListOf(mutableListOf())
    var j = 0

    list[j++] = mutableListOf(evolutionList[0])


    for (i in 1 until evolutionList.size) {
        list.add(mutableListOf())
        if (evolutionList[i].previousEvolutionId == evolutionList[i - 1].previousEvolutionId) {
            list[j].add(evolutionList[i])
        } else {
            list[i].add(evolutionList[i])
            ++j
        }
    }

    Log.i("POKEMON", "$list")

    return list
}
