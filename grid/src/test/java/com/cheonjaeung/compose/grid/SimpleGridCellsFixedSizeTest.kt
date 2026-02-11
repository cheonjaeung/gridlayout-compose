package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleGridCellsFixedSizeTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithZeroSize() {
        SimpleGridCells.FixedSize(0.dp)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithNegativeSize() {
        SimpleGridCells.FixedSize((-1).dp)
    }

    @Test
    fun testAvailableSizeIsEqualToSize() {
        val fixedSize = SimpleGridCells.FixedSize(100.dp)
        val availableSize = 100
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testAvailableSizeIsSmallerThanSize() {
        val fixedSize = SimpleGridCells.FixedSize(100.dp)
        val availableSize = 50
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testAvailableSizeIsLargerThanSize() {
        val fixedSize = SimpleGridCells.FixedSize(100.dp)
        val availableSize = 110
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(100), cellSizes)
    }

    @Test
    fun testNoRemainingPixels() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 100
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testHasRemainingPixels() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 120
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testNoRemainingPixelsWithSpacing() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 130
        val spacing = 10

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testHasRemainingPixelsWithSpacing() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 150
        val spacing = 10

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testCellCountCalculation() {
        val fixedSize = SimpleGridCells.FixedSize(50.dp)
        val spacing = 10

        val cellSizes1 = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(109, spacing)
            }
        }
        assertEquals(1, cellSizes1.size)

        val cellSizes2 = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(110, spacing)
            }
        }
        assertEquals(2, cellSizes2.size)
    }

    @Test
    fun testNegativeSpacing() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 100
        val spacing = -10

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testZeroAvailableSize() {
        val fixedSize = SimpleGridCells.FixedSize(25.dp)
        val availableSize = 0
        val spacing = 0

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
    }

    @Test
    fun testZeroCellSize() {
        val fixedSize = SimpleGridCells.FixedSize(30.dp)
        val availableSize = 100
        val spacing = -30

        val cellSizes = with(fixedSize) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
    }
}
