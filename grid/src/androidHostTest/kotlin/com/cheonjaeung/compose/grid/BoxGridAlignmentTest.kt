package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class BoxGridAlignmentTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testDefaultAlignment() {
        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Red
        )

        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3, false),
                    columns = SimpleGridCells.Fixed(3, false)
                ) {
                    for (row in 0 until 3) {
                        for (column in 0 until 3) {
                            val index = row * 3 + column

                            Box(
                                modifier = Modifier
                                    .position(row, column)
                                    .size(100.dp)
                                    .background(colors[index % 4])
                            )
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3, false),
                        columns = SimpleGridCells.Fixed(3, false)
                    ) {
                        for (row in 0 until 3) {
                            for (column in 0 until 3) {
                                val index = row * 3 + column

                                Box(
                                    modifier = Modifier
                                        .position(row, column)
                                        .size(100.dp)
                                        .background(colors[index % 4])
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testAlignModifiers() {
        val alignments = listOf(
            Alignment.TopStart,
            Alignment.CenterStart,
            Alignment.BottomStart,
            Alignment.TopCenter,
            Alignment.Center,
            Alignment.BottomCenter,
            Alignment.TopEnd,
            Alignment.CenterEnd,
            Alignment.BottomEnd
        )

        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Red
        )

        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3, false),
                    columns = SimpleGridCells.Fixed(3, false)
                ) {
                    for (row in 0 until 3) {
                        for (column in 0 until 3) {
                            val index = row * 3 + column
                            val alignment = alignments[index]

                            Box(
                                modifier = Modifier
                                    .position(row, column)
                                    .size(100.dp)
                                    .background(colors[index % 4])
                                    .align(alignment)
                            )
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3, false),
                        columns = SimpleGridCells.Fixed(3, false)
                    ) {
                        for (row in 0 until 3) {
                            for (column in 0 until 3) {
                                val index = row * 3 + column
                                val alignment = alignments[index]

                                Box(
                                    modifier = Modifier
                                        .position(row, column)
                                        .size(100.dp)
                                        .background(colors[index % 4])
                                        .align(alignment)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testContentAlignment() {
        val alignments = listOf(
            Alignment.TopStart,
            Alignment.CenterStart,
            Alignment.BottomStart,
            null,
            null,
            null,
            Alignment.TopEnd,
            Alignment.CenterEnd,
            Alignment.BottomEnd
        )

        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Red
        )

        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3, false),
                    columns = SimpleGridCells.Fixed(3, false),
                    contentAlignment = Alignment.Center
                ) {
                    for (row in 0 until 3) {
                        for (column in 0 until 3) {
                            val index = row * 3 + column
                            val alignment = alignments[index]

                            Box(
                                modifier = Modifier
                                    .position(row, column)
                                    .size(100.dp)
                                    .background(colors[index % 4])
                                    .then(
                                        if (alignment != null) {
                                            Modifier.align(alignment)
                                        } else {
                                            Modifier
                                        }
                                    )
                            )
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3, false),
                        columns = SimpleGridCells.Fixed(3, false),
                        contentAlignment = Alignment.Center
                    ) {
                        for (row in 0 until 3) {
                            for (column in 0 until 3) {
                                val index = row * 3 + column
                                val alignment = alignments[index]

                                Box(
                                    modifier = Modifier
                                        .position(row, column)
                                        .size(100.dp)
                                        .background(colors[index % 4])
                                        .then(
                                            if (alignment != null) {
                                                Modifier.align(alignment)
                                            } else {
                                                Modifier
                                            }
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
