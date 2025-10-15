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

@OptIn(ExperimentalGridApi::class)
class BoxGridTest {
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
                BoxGrid(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue),
                    rows = SimpleGridCells.Fixed(1),
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
                BoxGrid(
                    modifier = Modifier.background(Color.Blue),
                    rows = SimpleGridCells.Fixed(1),
                    columns = SimpleGridCells.Fixed(1),
                    content = {}
                )
            }
        }
    }

    @Test
    fun testDefaultRowColumnPosition() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3),
                    columns = SimpleGridCells.Fixed(3)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3),
                        columns = SimpleGridCells.Fixed(3)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testOverRowColumnPositionIsNotPlaced() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3),
                columns = SimpleGridCells.Fixed(3)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .position(row = 3, column = 3)
                        .background(Color.Blue)
                )
            }
        }
    }

    @Test
    fun testPlaceMultipleItemsIntoTheSamePosition() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Fixed(3, false),
                columns = SimpleGridCells.Fixed(3, false)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .position(row = 1, column = 1)
                        .background(Color.Blue)
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .position(row = 1, column = 1)
                        .background(Color.Green)
                )
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .position(row = 1, column = 1)
                        .background(Color.Yellow)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .position(row = 1, column = 1)
                        .background(Color.Red)
                )
            }
        }
    }

    @Test
    fun testFixedRowsFixedColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedRowsFixedColumnsWithFillFalse() {
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
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
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
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsFixedColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(100.dp),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(100.dp),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsFixedColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(100.dp, false),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(100.dp, false),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedRowsAdaptiveColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedRowsAdaptiveColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3, false),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(3, false),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsAdaptiveColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(100.dp),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(100.dp),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsAdaptiveColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(100.dp, false),
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
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(100.dp, false),
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
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedRowsAndFixedSizeColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(2),
                    columns = SimpleGridCells.FixedSize(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(2),
                        columns = SimpleGridCells.FixedSize(80.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedRowsAndFixedSizeColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(2, fill = false),
                    columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Fixed(2, fill = false),
                        columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsAndFixedSizeColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(60.dp),
                    columns = SimpleGridCells.FixedSize(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(60.dp),
                        columns = SimpleGridCells.FixedSize(80.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testAdaptiveRowsAndFixedSizeColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Adaptive(60.dp, fill = false),
                    columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.Adaptive(60.dp, fill = false),
                        columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedSizeRowsAndFixedSizeColumns() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.FixedSize(60.dp),
                    columns = SimpleGridCells.FixedSize(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.FixedSize(60.dp),
                        columns = SimpleGridCells.FixedSize(80.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testFixedSizeRowsAndFixedSizeColumnsWithFillFalse() {
        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    rows = SimpleGridCells.FixedSize(60.dp, fill = false),
                    columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Blue)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 1, column = 1)
                            .background(Color.Green)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 2, column = 2)
                            .background(Color.Yellow)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .position(row = 3, column = 1)
                            .background(Color.Red)
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BoxGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        rows = SimpleGridCells.FixedSize(60.dp, fill = false),
                        columns = SimpleGridCells.FixedSize(80.dp, fill = false)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Blue)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 1, column = 1)
                                .background(Color.Green)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 2, column = 2)
                                .background(Color.Yellow)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .position(row = 3, column = 1)
                                .background(Color.Red)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testSpacing() {
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
                        .fillMaxSize()
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalSpacing = 8.dp,
                    verticalSpacing = 16.dp
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

    @Test
    fun testNegativeSpacing() {
        val colors = listOf(
            Color.Blue.copy(alpha = 0.5f),
            Color.Green.copy(alpha = 0.5f),
            Color.Yellow.copy(alpha = 0.5f),
            Color.Red.copy(alpha = 0.5f)
        )

        paparazzi.snapshot {
            Column {
                BoxGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    rows = SimpleGridCells.Fixed(3),
                    columns = SimpleGridCells.Fixed(3),
                    horizontalSpacing = (-8).dp,
                    verticalSpacing = (-16).dp
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

    @Test
    fun testAdaptiveMinSizeEqualsNegativeSpacing() {
        paparazzi.snapshot {
            BoxGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                rows = SimpleGridCells.Adaptive(100.dp),
                columns = SimpleGridCells.Adaptive(100.dp),
                horizontalSpacing = (-100).dp,
                verticalSpacing = (-100).dp,
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}
