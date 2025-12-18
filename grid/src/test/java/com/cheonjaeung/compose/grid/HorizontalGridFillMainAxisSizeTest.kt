package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalGridApi::class)
class HorizontalGridFillMainAxisSizeTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testFillMaxMainAxisSize() {
        paparazzi.snapshot {
            HorizontalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .fillMaxMainAxisSize()
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                )
            }
        }
    }

    @Test
    fun testFillMaxMainAxisSizeNotApplied() {
        paparazzi.snapshot {
            HorizontalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                )
            }
        }
    }

    @Test
    fun testFillMaxMainAxisSizeWithFraction() {
        paparazzi.snapshot {
            HorizontalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .fillMaxMainAxisSize(fraction = 0.75f)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                )
            }
        }
    }

    @Test
    fun testAllItemsAreFillMaxMainAxisSizeApplied() {
        paparazzi.snapshot {
            HorizontalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .fillMaxMainAxisSize()
                        .background(Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .fillMaxMainAxisSize()
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .fillMaxMainAxisSize()
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.Yellow)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}
