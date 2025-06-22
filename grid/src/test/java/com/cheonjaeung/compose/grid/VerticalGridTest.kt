package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

class VerticalGridTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testEmptyWithSize() {
        paparazzi.snapshot {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                VerticalGrid(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue),
                    columns = SimpleGridCells.Fixed(1),
                    content = {}
                )
            }
        }
    }

    @Test
    fun testEmptyWithoutSize() {
        paparazzi.snapshot {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                VerticalGrid(
                    modifier = Modifier.background(Color.Blue),
                    columns = SimpleGridCells.Fixed(1),
                    content = {}
                )
            }
        }
    }

    @Test
    fun testFixedColumns() {
        paparazzi.snapshot {
            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3, false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Fixed(3, false)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveColumns() {
        paparazzi.snapshot {
            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Adaptive(120.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Adaptive(120.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Adaptive(120.dp, false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        columns = SimpleGridCells.Adaptive(120.dp, false)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testBreakDownToNextLine() {
        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Red,
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Red
        )

        paparazzi.snapshot {
            Column {
                VerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    columns = SimpleGridCells.Fixed(3)
                ) {
                    for (i in 0 until 8) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(colors[i])
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
                        for (i in 0 until 8) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(colors[i])
                            )
                        }
                    }
                }
            }
        }
    }
}
