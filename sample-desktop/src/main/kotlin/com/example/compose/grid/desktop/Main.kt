package com.example.compose.grid.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.random.Random

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Grid Sample",
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = DpSize(1200.dp, 800.dp)
        )
    ) {
        var itemCount by remember { mutableStateOf(12) }
        var useRandomSize by remember { mutableStateOf(true) }
        var layoutDirection: LayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
        var orientation: Orientation by remember { mutableStateOf(Orientation.Vertical) }
        var horizontalArrangement: Arrangement.Horizontal by remember {
            mutableStateOf(Arrangement.Start)
        }
        var verticalArrangement: Arrangement.Vertical by remember {
            mutableStateOf(Arrangement.Top)
        }

        val items by remember(itemCount, useRandomSize) {
            mutableStateOf(createGridItems(count = itemCount, useRandomSize = useRandomSize))
        }

        MaterialTheme {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SamplePane(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    items = items,
                    layoutDirection = layoutDirection,
                    gridHorizontalArrangement = horizontalArrangement,
                    gridVerticalArrangement = verticalArrangement,
                    isVertical = orientation == Orientation.Vertical,
                )
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)
                )
                OptionPane(
                    modifier = Modifier
                        .width(400.dp)
                        .fillMaxHeight(),
                    itemCount = itemCount,
                    onItemCountChange = { itemCount = it },
                    useRandomSize = useRandomSize,
                    onUseRandomSizeChange = { useRandomSize = it },
                    layoutDirection = layoutDirection,
                    onLayoutDirectionChange = { layoutDirection = it },
                    orientation = orientation,
                    onOrientationChange = { orientation = it },
                    horizontalArrangement = horizontalArrangement,
                    onHorizontalArrangementChange = { horizontalArrangement = it },
                    verticalArrangement = verticalArrangement,
                    onVerticalArrangementChange = { verticalArrangement = it },
                )
            }
        }
    }
}

private fun createGridItems(
    count: Int,
    useRandomSize: Boolean = false,
): List<GridItemInfo> {
    val items = mutableListOf<GridItemInfo>()
    val fixedSize = 80.dp
    for (i in 0 until count) {
        items.add(
            GridItemInfo(
                color = randomColor(),
                width = if (useRandomSize) randomSize() else fixedSize,
                height = if (useRandomSize) randomSize() else fixedSize,
            )
        )
    }
    return items
}

private fun randomColor(): Color {
    val randomRed = Random.nextInt(150, 220)
    val randomGreen = Random.nextInt(150, 220)
    val randomBlue = Random.nextInt(150, 220)
    return Color(red = randomRed, green = randomGreen, blue = randomBlue, alpha = 127)
}

private fun randomSize(): Dp {
    return Dp(Random.nextInt(60, 100).toFloat())
}
