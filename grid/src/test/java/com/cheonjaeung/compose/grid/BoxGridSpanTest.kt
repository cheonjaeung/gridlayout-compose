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
class BoxGridSpanTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6
    )

    @Test
    fun testDefaultSpan() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                )
            }
        }
    }

    @Test
    fun testOneSpan() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .rowSpan { 1 }
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                        .rowSpan { 1 }
                        .columnSpan { 1 }
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                        .columnSpan { 1 }
                )
            }
        }
    }

    @Test
    fun testTwoSpan() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .rowSpan { 2 }
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                        .rowSpan { 2 }
                        .columnSpan { 2 }
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                        .columnSpan { 2 }
                )
            }
        }
    }

    @Test
    fun testOverSpanItemIsNotPlaced() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .rowSpan { 2 }
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                        .rowSpan { 5 }
                        .columnSpan { 5 }
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                        .columnSpan { 2 }
                )
            }
        }
    }

    @Test
    fun testMaxLineSpan() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .rowSpan { maxLineSpan }
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                )
            }
        }
    }

    @Test
    fun testMaxCurrentLineSpan() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(4),
                columns = SimpleGridCells.Fixed(4)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )
                Box(
                    modifier = Modifier
                        .row(1)
                        .column(1)
                        .size(100.dp)
                        .background(Color.Green)
                        .rowSpan { maxCurrentLineSpan }
                )
                Box(
                    modifier = Modifier
                        .row(2)
                        .column(2)
                        .size(100.dp)
                        .background(Color.Yellow)
                        .columnSpan { maxCurrentLineSpan }
                )
            }
        }
    }
}
