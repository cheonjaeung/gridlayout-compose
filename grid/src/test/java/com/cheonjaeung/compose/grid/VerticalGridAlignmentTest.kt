package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

class VerticalGridAlignmentTest {
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
            Color.Transparent
        )

        paparazzi.snapshot {
            Column {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    val cellHeight = this.maxHeight / 3

                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        columns = SimpleGridCells.Fixed(4, false)
                    ) {
                        for (i in 0 until 16) {
                            if (i % 4 == 3) {
                                Box(modifier = Modifier.height(cellHeight))
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(colors[i % 4])
                                )
                            }
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxWithConstraints(modifier = Modifier.weight(1f)) {
                        val cellHeight = this.maxHeight / 3

                        VerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray),
                            columns = SimpleGridCells.Fixed(4, false)
                        ) {
                            for (i in 0 until 16) {
                                if (i % 4 == 3) {
                                    Box(modifier = Modifier.height(cellHeight))
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .background(colors[i % 4])
                                    )
                                }
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
            null,
            Alignment.TopCenter,
            Alignment.Center,
            Alignment.BottomCenter,
            null,
            Alignment.TopEnd,
            Alignment.CenterEnd,
            Alignment.BottomEnd,
            null
        )

        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Transparent
        )

        paparazzi.snapshot {
            Column {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    val cellHeight = this.maxHeight / 3

                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        columns = SimpleGridCells.Fixed(4, false)
                    ) {
                        for ((index, alignment) in alignments.withIndex()) {
                            if (alignment == null) {
                                Box(
                                    Modifier.height(cellHeight)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(colors[index % 4])
                                        .align(alignment)
                                )
                            }
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxWithConstraints(modifier = Modifier.weight(1f)) {
                        val cellHeight = this.maxHeight / 3

                        VerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray),
                            columns = SimpleGridCells.Fixed(4, false)
                        ) {
                            for ((index, alignment) in alignments.withIndex()) {
                                if (alignment == null) {
                                    Box(
                                        Modifier.height(cellHeight)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
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
            null,
            null,
            Alignment.TopEnd,
            Alignment.CenterEnd,
            Alignment.BottomEnd,
            null
        )

        val colors = listOf(
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Transparent
        )

        paparazzi.snapshot {
            Column {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    val cellHeight = this.maxHeight / 3

                    VerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        columns = SimpleGridCells.Fixed(4, false),
                        contentAlignment = Alignment.Center
                    ) {
                        for (i in alignments.indices) {
                            if (i % 4 == 3) {
                                Box(
                                    Modifier.height(cellHeight)
                                )
                            } else {
                                val alignment = alignments[i]
                                Box(
                                    modifier = if (alignment != null) {
                                        Modifier
                                            .size(80.dp)
                                            .background(colors[i % 4])
                                            .align(alignment)
                                    } else {
                                        Modifier
                                            .size(80.dp)
                                            .background(colors[i % 4])
                                    }
                                )
                            }
                        }
                    }
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxWithConstraints(modifier = Modifier.weight(1f)) {
                        val cellHeight = this.maxHeight / 3

                        VerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray),
                            columns = SimpleGridCells.Fixed(4, false),
                            contentAlignment = Alignment.Center
                        ) {
                            for (i in alignments.indices) {
                                if (i % 4 == 3) {
                                    Box(
                                        Modifier.height(cellHeight)
                                    )
                                } else {
                                    val alignment = alignments[i]
                                    Box(
                                        modifier = if (alignment != null) {
                                            Modifier
                                                .size(80.dp)
                                                .background(colors[i % 4])
                                                .align(alignment)
                                        } else {
                                            Modifier
                                                .size(80.dp)
                                                .background(colors[i % 4])
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
