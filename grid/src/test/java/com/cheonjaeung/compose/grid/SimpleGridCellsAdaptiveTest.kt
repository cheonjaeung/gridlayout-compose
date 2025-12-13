package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleGridCellsAdaptiveTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithZeroMinSize() {
        SimpleGridCells.Adaptive(0.dp)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithNegativeMinSize() {
        SimpleGridCells.Adaptive((-1).dp)
    }

    @Test
    fun testAvailableSizeIsEqualToMinSize() {
        val adaptive = SimpleGridCells.Adaptive(100.dp)
        val availableSize = 100
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testAvailableSizeIsSmallerThanMinSize() {
        val adaptive = SimpleGridCells.Adaptive(100.dp)
        val availableSize = 50
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testAvailableSizeIsLargetThanMinSize() {
        val adaptive = SimpleGridCells.Adaptive(100.dp)
        val availableSize = 110
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testNoRemainingPixels() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 120
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(30, 30, 30, 30), cellSizes)
    }

    @Test
    fun testHasRemainingPixels() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 123
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(31, 31, 31, 30), cellSizes)
    }

    @Test
    fun testNoRemainingPixelsWithSpacing() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 150
        val spacing = 10

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(30, 30, 30, 30), cellSizes)
    }

    @Test
    fun testHasRemainingPixelsWithSpacing() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 160
        val spacing = 10

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(33, 33, 32, 32), cellSizes)
    }

    @Test
    fun testCellCountCalculation() {
        val adaptive = SimpleGridCells.Adaptive(50.dp)
        val spacing = 10

        val cellSizes1 = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(109, spacing)
            }
        }
        assertEquals(1, cellSizes1.size)

        val cellSizes2 = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(110, spacing)
            }
        }
        assertEquals(2, cellSizes2.size)
    }

    @Test
    fun testNegativeSpacing() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 100
        val spacing = -10

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testZeroAvailableSize() {
        val adaptive = SimpleGridCells.Adaptive(25.dp)
        val availableSize = 0
        val spacing = 0

        val cellSizes = with(adaptive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
    }
}
