package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import org.junit.Assert.assertEquals
import org.junit.Test
class SimpleGridCellsFixedTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithZeroCount() {
        SimpleGridCells.Fixed(0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorWithNegativeCount() {
        SimpleGridCells.Fixed(-1)
    }

    @Test
    fun testCountIsOne() {
        val fixed = SimpleGridCells.Fixed(1)
        val availableSize = 100
        val spacing = 10

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(availableSize), cellSizes)
    }

    @Test
    fun testNoRemainingPixels() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 100
        val spacing = 0

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(25, 25, 25, 25), cellSizes)
    }

    @Test
    fun testHasRemainingPixels() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 103
        val spacing = 0

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(26, 26, 26, 25), cellSizes)
    }

    @Test
    fun testNoRemainingPixelsWithSpacing() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 110
        val spacing = 10

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(20, 20, 20, 20), cellSizes)
    }

    @Test
    fun testHasRemainingPixelsWithSpacing() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 113
        val spacing = 10

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(21, 21, 21, 20), cellSizes)
    }

    @Test
    fun testNegativeSpacing() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 100
        val spacing = -10

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(listOf(33, 33, 32, 32), cellSizes)
    }

    @Test
    fun testZeroAvailableSize() {
        val fixed = SimpleGridCells.Fixed(4)
        val availableSize = 0
        val spacing = 0

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
    }

    @Test
    fun testZeroCellSize() {
        val fixed = SimpleGridCells.Fixed(3)
        val availableSize = 100
        val spacing = 50

        val cellSizes = with(fixed) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
    }
}
