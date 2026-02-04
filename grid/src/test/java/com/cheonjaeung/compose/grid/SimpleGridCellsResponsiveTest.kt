package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalGridApi::class)
class SimpleGridCellsResponsiveTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test
    fun testResponsive() {
        val responsive = SimpleGridCells.Responsive { availableWidth ->
            if (availableWidth < 150.dp) {
                SimpleGridCells.Fixed(1)
            } else {
                SimpleGridCells.Fixed(2)
            }
        }

        val availableSizeSmall = 100
        val cellSizesSmall = with(responsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeSmall, 0)
            }
        }
        assertEquals(listOf(availableSizeSmall), cellSizesSmall)

        val availableSizeLarge = 200
        val expectedEachSize = availableSizeLarge / 2
        val cellSizesLarge = with(responsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeLarge, 0)
            }
        }
        assertEquals(listOf(expectedEachSize, expectedEachSize), cellSizesLarge)
    }

    @Test
    fun testResponsiveFactoryDensityReceiver() {
        val responsive = SimpleGridCells.Responsive { availableWidth ->
            val availablePx = availableWidth.toPx()
            if (availablePx < 500f) {
                SimpleGridCells.Fixed(1)
            } else {
                SimpleGridCells.Fixed(3)
            }
        }

        val cellSizes = with(responsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(600, 0)
            }
        }
        assertEquals(listOf(200, 200, 200), cellSizes)
    }

    @Test
    fun testZeroAvailableSize() {
        var factoryCalled = false
        val responsive = SimpleGridCells.Responsive {
            factoryCalled = true
            SimpleGridCells.Fixed(1)
        }

        val cellSizes = with(responsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(0, 0)
            }
        }

        assertEquals(emptyList<Int>(), cellSizes)
        assertEquals(false, factoryCalled)
    }
}
