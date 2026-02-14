package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalGridApi::class)
class SimpleGridCellsTrackTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test
    fun testTrackNoTracks() {
        val track = ExtendedGridCells.SimpleGridCells.Track(emptyList())
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun testTrackOnlyFixed() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(20.dp),
            GridTrack.Fixed(30.dp),
            GridTrack.Fixed(50.dp)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 200, spacing = 0)
            }
        }
        assertEquals(listOf(20, 30, 50), result)
    }

    @Test
    fun testTrackOnlyWeight() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(2f),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 400, spacing = 0)
            }
        }
        assertEquals(listOf(100, 200, 100), result)
    }

    @Test
    fun testTrackOnlyWeightsWithRemainingPixels() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        assertEquals(listOf(33, 34, 33), result)
        assertEquals(100, result.sum())
    }

    @Test
    fun testTrackMixed() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        assertEquals(listOf(100, 100, 100), result)
    }

    @Test
    fun testTrackMixedWithRemainingPixels() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(95.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        assertEquals(listOf(95, 103, 102), result)
    }

    @Test
    fun testTrackWithSpacing() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 5)
            }
        }
        assertEquals(listOf(100, 95, 95), result)
    }

    @Test
    fun testTrackInsufficientSpace() {
        val track = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(200.dp),
            GridTrack.Weight(1f)
        )
        val result = with(track) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        assertEquals(listOf(200, 0), result)
    }
}
