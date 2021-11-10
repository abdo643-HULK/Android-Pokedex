package com.shehata.pokedex.screens.pokemondetails

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shehata.pokedex.models.Stats

private val STAT_NAMES =
    arrayOf("HP", "ATTACK", "DEFENSE", "SPEED", "SPECIAL-ATTACK", "SPECIAL-DEFENSE")

@Composable
fun StatsBox(
    stats: Stats,
    color: Color,
    selected: StatTypes,
    onStatChange: (StatTypes) -> Unit,
) {
    val arrayFromStats by remember(stats) {
        derivedStateOf {
            arrayOf(
                stats.hp,
                stats.attack,
                stats.defense,
                stats.speed,
                stats.specialAttack,
                stats.specialDefense
            )
        }
    }

    SelectBar(
        color = color,
        selected = selected,
        onSelect = onStatChange
    )

    Column(
        modifier = Modifier.padding(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        for ((index, statValue) in arrayFromStats.withIndex()) {

            StatsBar(
                statName = STAT_NAMES[index],
                statValue = statValue,
                color = color,
                percentage = (statValue.toFloat() / stats.biggestStat.toFloat())
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun SelectBar(
    color: Color,
    selected: StatTypes,
    onSelect: (StatTypes) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (statType in StatTypes.values()) {
            StatsButton(
                text = statType.toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                color = color,
                selected = selected == statType,
                onClick = {
                    if (selected == statType) return@StatsButton
                    onSelect(statType)
                }
            )
        }
    }
}

private val StatsButtonHorizontalPadding = 10.dp
private val StatsButtonVerticalPadding = 10.dp

@Composable
fun StatsButton(
    text: String,
    color: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val roundedCornerRadius = RoundedCornerShape(35)
    val currentColor = remember { Animatable(Color.Transparent) }

    LaunchedEffect(selected) {
        currentColor.animateTo(if (selected) color else Color.Transparent)
    }

    Button(
        onClick = onClick,
        shape = roundedCornerRadius,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = currentColor.value,
            contentColor = if (selected) MaterialTheme.colors.primary else color
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .background(currentColor.value)
                .padding(
                    top = StatsButtonVerticalPadding,
                    bottom = StatsButtonVerticalPadding,
                    start = StatsButtonHorizontalPadding,
                    end = StatsButtonHorizontalPadding
                ),
        )
    }
}

@Composable
fun StatsBar(
    statName: String,
    statValue: UInt,
    color: Color,
    percentage: Float,
    modifier: Modifier = Modifier.height(30.dp)
) {
    val roundedCornerRadius = RoundedCornerShape(20)


    Text(
        text = statName,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        modifier = Modifier.fillMaxWidth(),
//        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundedCornerRadius)
            .background(color.copy(0.24f))
    ) {
        val animatedPercentage by animateFloatAsState(
            targetValue = percentage,
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        )
        Box(
            modifier = modifier
                .width(this.maxWidth * animatedPercentage)
                .clip(roundedCornerRadius)
                .background(color)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
//                BasicText(
//                    text = statName,
//                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight(600))
//                )

                BasicText(
                    text = "$statValue",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}
