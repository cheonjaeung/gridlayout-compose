package com.cheonjaeung.compose.grid

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalGridApi::class)
class SimpleGridCellsResponsiveTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test
    fun testResponsive() {
        val simpleResponsive = ExtendedGridCells.SimpleGridCells.Responsive { availableWidth ->
            if (availableWidth < 150.dp) {
                SimpleGridCells.Fixed(1)
            } else {
                SimpleGridCells.Fixed(2)
            }
        }
        val gridResponsive = ExtendedGridCells.GridCells.Responsive { availableWidth ->
            if (availableWidth < 150.dp) {
                GridCells.Fixed(1)
            } else {
                GridCells.Fixed(2)
            }
        }
        val staggeredResponsive = ExtendedGridCells.StaggeredGridCells.Responsive { availableWidth ->
            if (availableWidth < 150.dp) {
                StaggeredGridCells.Fixed(1)
            } else {
                StaggeredGridCells.Fixed(2)
            }
        }

        val availableSizeSmall = 100
        val simpleCellSizesSmall = with(simpleResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeSmall, 0)
            }
        }
        val gridCellSizesSmall = with(gridResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeSmall, 0)
            }
        }
        val staggeredCellSizesSmall = with(staggeredResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeSmall, 0)
            }
        }
        assertEquals(listOf(availableSizeSmall), simpleCellSizesSmall)
        assertEquals(listOf(availableSizeSmall), gridCellSizesSmall)
        assertArrayEquals(intArrayOf(availableSizeSmall), staggeredCellSizesSmall)

        val availableSizeLarge = 200
        val expectedEachSize = availableSizeLarge / 2
        val simpleCellSizesLarge = with(simpleResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeLarge, 0)
            }
        }
        val gridCellSizesLarge = with(gridResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeLarge, 0)
            }
        }
        val staggeredCellSizesLarge = with(staggeredResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSizeLarge, 0)
            }
        }
        assertEquals(listOf(expectedEachSize, expectedEachSize), simpleCellSizesLarge)
        assertEquals(listOf(expectedEachSize, expectedEachSize), gridCellSizesLarge)
        assertArrayEquals(intArrayOf(expectedEachSize, expectedEachSize), staggeredCellSizesLarge)
    }

    @Test
    fun testResponsiveFactoryDensityReceiver() {
        val simpleResponsive = ExtendedGridCells.SimpleGridCells.Responsive { availableWidth ->
            val availablePx = availableWidth.toPx()
            if (availablePx < 500f) {
                SimpleGridCells.Fixed(1)
            } else {
                SimpleGridCells.Fixed(3)
            }
        }
        val gridResponsive = ExtendedGridCells.GridCells.Responsive { availableWidth ->
            val availablePx = availableWidth.toPx()
            if (availablePx < 500f) {
                GridCells.Fixed(1)
            } else {
                GridCells.Fixed(3)
            }
        }
        val staggeredResponsive = ExtendedGridCells.StaggeredGridCells.Responsive { availableWidth ->
            val availablePx = availableWidth.toPx()
            if (availablePx < 500f) {
                StaggeredGridCells.Fixed(1)
            } else {
                StaggeredGridCells.Fixed(3)
            }
        }

        val simpleCellSizes = with(simpleResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(600, 0)
            }
        }
        val gridCellSizes = with(gridResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(600, 0)
            }
        }
        val staggeredCellSizes = with(staggeredResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(600, 0)
            }
        }
        assertEquals(listOf(200, 200, 200), simpleCellSizes)
        assertEquals(listOf(200, 200, 200), gridCellSizes)
        assertArrayEquals(intArrayOf(200, 200, 200), staggeredCellSizes)
    }

    @Test
    fun testZeroAvailableSize() {
        var simpleFactoryCalled = false
        val simpleResponsive = ExtendedGridCells.SimpleGridCells.Responsive {
            simpleFactoryCalled = true
            SimpleGridCells.Fixed(1)
        }
        var gridFactoryCalled = false
        val gridResponsive = ExtendedGridCells.GridCells.Responsive {
            gridFactoryCalled = true
            GridCells.Fixed(1)
        }
        var staggeredFactoryCalled = false
        val staggeredResponsive = ExtendedGridCells.StaggeredGridCells.Responsive {
            staggeredFactoryCalled = true
            StaggeredGridCells.Fixed(1)
        }

        val simpleCellSizes = with(simpleResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(0, 0)
            }
        }
        val gridCellSizes = with(gridResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(0, 0)
            }
        }
        val staggeredCellSizes = with(staggeredResponsive) {
            with(testDensity) {
                calculateCrossAxisCellSizes(0, 0)
            }
        }

        assertEquals(emptyList<Int>(), simpleCellSizes)
        assertEquals(false, simpleFactoryCalled)
        assertEquals(emptyList<Int>(), gridCellSizes)
        assertEquals(false, gridFactoryCalled)
        assertArrayEquals(intArrayOf(), staggeredCellSizes)
        assertEquals(false, staggeredFactoryCalled)
    }
}
