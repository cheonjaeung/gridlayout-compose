package com.example.compose.grid.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
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
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid

data class GridItemInfo(
    val color: Color,
    val width: Dp,
    val height: Dp,
)

@Composable
fun SampleScreen(
    items: List<GridItemInfo>,
    isVertical: Boolean,
    layoutDirection: LayoutDirection,
    gridHorizontalArrangement: Arrangement.Horizontal,
    gridVerticalArrangement: Arrangement.Vertical,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalLayoutDirection provides layoutDirection
        ) {
            if (isVertical) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState),
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
                    rows = SimpleGridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .horizontalScroll(scrollState),
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

        Box(modifier = Modifier.padding(16.dp)) {
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClick,
            ) {
                Text(text = "Options")
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
