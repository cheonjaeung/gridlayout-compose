package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class VerticalGridArrangementTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testDefaultArrangement() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3)
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3)
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testStartTop() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testCenterCenter() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testEndBottom() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testSpaceAround() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testSpaceBetween() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testSpaceEvenly() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testSpacedBy() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue,
                Color.Green,
                Color.Yellow,
                Color.Red
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testNegativeSpacedBy() {
        paparazzi.snapshot {
            val colors = listOf(
                Color.Blue.copy(alpha = 0.5f),
                Color.Green.copy(alpha = 0.5f),
                Color.Yellow.copy(alpha = 0.5f),
                Color.Red.copy(alpha = 0.5f)
            )

            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy((-8).dp),
                    verticalArrangement = Arrangement.spacedBy((-16).dp)
                ) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i % 4])
                        )
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy((-8).dp),
                        verticalArrangement = Arrangement.spacedBy((-16).dp)
                    ) {
                        for (i in 0 until 9) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i % 4])
                            )
                        }
                    }
                }
            }
        }
    }
}
