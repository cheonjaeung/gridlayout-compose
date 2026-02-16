package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalGridApi::class)
class SimpleGridCellsTrackTest {

    private val testDensity = Density(density = 1f, fontScale = 1f)

    @Test
    fun testTrackNoTracks() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(emptyList())
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(emptyList())
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(emptyList())

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }

        assertEquals(emptyList<Int>(), simpleGridCellsResult)
        assertEquals(emptyList<Int>(), gridCellsResult)
        assertArrayEquals(intArrayOf(), staggeredGridCellsResult)
    }

    @Test
    fun testTrackOnlyFixed() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(20.dp),
            GridTrack.Fixed(30.dp),
            GridTrack.Fixed(50.dp)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Fixed(20.dp),
            GridTrack.Fixed(30.dp),
            GridTrack.Fixed(50.dp)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Fixed(20.dp),
            GridTrack.Fixed(30.dp),
            GridTrack.Fixed(50.dp)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 200, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 200, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 200, spacing = 0)
            }
        }

        assertEquals(listOf(20, 30, 50), simpleGridCellsResult)
        assertEquals(listOf(20, 30, 50), gridCellsResult)
        assertArrayEquals(intArrayOf(20, 30, 50), staggeredGridCellsResult)
    }

    @Test
    fun testTrackOnlyWeight() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(2f),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(2f),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(2f),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 400, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 400, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 400, spacing = 0)
            }
        }

        assertEquals(listOf(100, 200, 100), simpleGridCellsResult)
        assertEquals(listOf(100, 200, 100), gridCellsResult)
        assertArrayEquals(intArrayOf(100, 200, 100), staggeredGridCellsResult)
    }

    @Test
    fun testTrackOnlyWeightsWithRemainingPixels() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Weight(1f),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }

        assertEquals(listOf(33, 34, 33), simpleGridCellsResult)
        assertEquals(100, simpleGridCellsResult.sum())

        assertEquals(listOf(33, 34, 33), gridCellsResult)
        assertEquals(100, gridCellsResult.sum())

        assertArrayEquals(intArrayOf(33, 34, 33), staggeredGridCellsResult)
        assertEquals(100, staggeredGridCellsResult.sum())
    }

    @Test
    fun testTrackMixed() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }

        assertEquals(listOf(100, 100, 100), simpleGridCellsResult)
        assertEquals(listOf(100, 100, 100), gridCellsResult)
        assertArrayEquals(intArrayOf(100, 100, 100), staggeredGridCellsResult)
    }

    @Test
    fun testTrackMixedWithRemainingPixels() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(95.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Fixed(95.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Fixed(95.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 0)
            }
        }

        assertEquals(listOf(95, 103, 102), simpleGridCellsResult)
        assertEquals(listOf(95, 103, 102), gridCellsResult)
        assertArrayEquals(intArrayOf(95, 103, 102), staggeredGridCellsResult)
    }

    @Test
    fun testTrackWithSpacing() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Fixed(100.dp),
            GridTrack.Weight(1f),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 5)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 5)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 300, spacing = 5)
            }
        }

        assertEquals(listOf(100, 95, 95), simpleGridCellsResult)
        assertEquals(listOf(100, 95, 95), gridCellsResult)
        assertArrayEquals(intArrayOf(100, 95, 95), staggeredGridCellsResult)
    }

    @Test
    fun testTrackInsufficientSpace() {
        val simpleGridCellsTrack = ExtendedGridCells.SimpleGridCells.Track(
            GridTrack.Fixed(200.dp),
            GridTrack.Weight(1f)
        )
        val gridCellsTrack = ExtendedGridCells.GridCells.Track(
            GridTrack.Fixed(200.dp),
            GridTrack.Weight(1f)
        )
        val staggeredGridCellsTrack = ExtendedGridCells.StaggeredGridCells.Track(
            GridTrack.Fixed(200.dp),
            GridTrack.Weight(1f)
        )

        val simpleGridCellsResult = with(simpleGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val gridCellsResult = with(gridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }
        val staggeredGridCellsResult = with(staggeredGridCellsTrack) {
            with(testDensity) {
                calculateCrossAxisCellSizes(availableSize = 100, spacing = 0)
            }
        }

        assertEquals(listOf(200, 0), simpleGridCellsResult)
        assertEquals(listOf(200, 0), gridCellsResult)
        assertArrayEquals(intArrayOf(200, 0), staggeredGridCellsResult)
    }
}
