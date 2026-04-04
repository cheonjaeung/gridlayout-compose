package com.cheonjaeung.compose.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class VerticalGridSpanTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testDefaultSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
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
            }
        }
    }

    @Test
    fun testOneSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )
            }
        }
    }

    @Test
    fun testTwoSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { 2 }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )
            }
        }
    }

    @Test
    fun testBreakDownToNextLine() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { 3 }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )
            }
        }
    }

    @Test
    fun testMaxLineSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(6)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { maxLineSpan }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )
            }
        }
    }

    @Test
    fun testMaxCurrentLineSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(6)
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
                        .span { maxCurrentLineSpan }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Red)
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { maxCurrentLineSpan }
                )
            }
        }
    }

    @Test
    fun testOverSpanItemIsNotPlaced() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { maxLineSpan + 1 }
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )
            }
        }
    }

    @Test
    fun testOverrideSpan() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .span { 1 }
                        .span { 2 }
                )
            }
        }
    }

    @Test
    @OptIn(ExperimentalGridApi::class)
    fun testSpanSizeWithTrackColumns() {
        paparazzi.snapshot {
            VerticalGrid(
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .background(Color.LightGray),
                columns = ExtendedGridCells.SimpleGridCells.Track(
                    GridTrack.Fixed(100.dp),
                    GridTrack.Fixed(200.dp)
                )
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .span { maxLineSpan }
                        .background(Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .background(Color.Green)
                )
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .background(Color.Red)
                )
            }
        }
    }
}
