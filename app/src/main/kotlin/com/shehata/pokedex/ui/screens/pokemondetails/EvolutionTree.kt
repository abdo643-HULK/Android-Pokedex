package com.shehata.pokedex.ui.screens.pokemondetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import com.shehata.pokedex.R
import com.shehata.pokedex.models.Evolution

@Composable
fun EvolutionTree(
    evolutions: List<Evolution>,
    painters: List<ImagePainter>
) {
    PokemonBoxShadow(
        isDarkTheme = isSystemInDarkTheme(),
        cornerRadius = 6,
        modifier = Modifier.padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            val evolutionMatrix = remember { getEvolutionMatrix(evolutions) }
            val max = remember { evolutionMatrix.maxByOrNull { it.size } }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Evolutions",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h3,
                )

                Spacer(modifier = Modifier.height(5.dp))

                evolutionMatrix.forEachIndexed { i, evolutionList ->
                    if (evolutionList.size < 4) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            evolutionList.forEachIndexed { j, evolution ->
                                val painter = painters[i + j]
                                Image(
                                    painter = painter,
                                    contentDescription = "Image of ${evolution.name}",
                                    modifier = Modifier
                                        .height(130.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    } else {
                        var ix = i;
                        evolutionList.chunked(3).forEachIndexed { j, evolutionsChunked ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                evolutionsChunked.forEachIndexed { n, evolution ->
                                    val painter = painters[ix++]
                                    Image(
                                        painter = painter,
                                        contentDescription = "Image of ${evolution.name}",
                                        modifier = Modifier
                                            .height(130.dp)
                                            .weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    if (i == evolutionMatrix.size - 1) return@forEachIndexed

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_round_arrow_downward_24),
                            contentDescription = "Next Evolution",
                            modifier = Modifier.size(50.dp)
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
