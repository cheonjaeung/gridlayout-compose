package com.example.compose.grid.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.woong.compose.grid.HorizontalGrid
import io.woong.compose.grid.VerticalGrid

data class GridItemInfo(
    val color: Color,
    val width: Dp,
    val height: Dp,
)

@Composable
fun SamplePane(
    items: List<GridItemInfo>,
    isVertical: Boolean,
    layoutDirection: LayoutDirection,
    gridHorizontalArrangement: Arrangement.Horizontal,
    gridVerticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalLayoutDirection provides layoutDirection
        ) {
            if (isVertical) {
                VerticalGrid(
                    columnCount = 3,
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = gridHorizontalArrangement,
                    verticalArrangement = gridVerticalArrangement,
                ) {
                    for ((index, item) in items.withIndex()) {
                        GridItem(
                            color = item.color,
                            modifier = Modifier.size(item.width, item.height),
                            text = (index + 1).toString()
                        )
                    }
                }
            } else {
                HorizontalGrid(
                    rowCount = 3,
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = gridHorizontalArrangement,
                    verticalArrangement = gridVerticalArrangement,
                ) {
                    for ((index, item) in items.withIndex()) {
                        GridItem(
                            color = item.color,
                            modifier = Modifier.size(item.width, item.height),
                            text = (index + 1).toString()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GridItem(
    color: Color,
    modifier: Modifier = Modifier,
    text: String = "",
) {
    val shape = RoundedCornerShape(4.dp)
    Box(
        modifier = modifier.then(
            Modifier
                .clip(shape)
                .border(width = 2.dp, color = color.copy(alpha = 0.75f), shape = shape)
                .background(color = color)
        )
    ) {
        BasicText(
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
