package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun SampleItem(
    color: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(4.dp)

    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 2.dp, color = color.copy(alpha = 0.75f), shape = shape)
            .background(color = color)
            .then(modifier)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

fun randomColor(): Color {
    val randomRed = Random.nextInt(150, 220)
    val randomGreen = Random.nextInt(150, 220)
    val randomBlue = Random.nextInt(150, 220)
    return Color(red = randomRed, green = randomGreen, blue = randomBlue, alpha = 127)
}
