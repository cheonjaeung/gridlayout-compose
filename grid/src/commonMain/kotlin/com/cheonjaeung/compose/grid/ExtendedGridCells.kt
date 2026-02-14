package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A class determines the sizes and the number of cells for extended capabilities.
 */
@Stable
@ExperimentalGridApi
interface ExtendedGridCells {
    /**
     * Extended cell management for [com.cheonjaeung.compose.grid.SimpleGridCells].
     */
    @Stable
    @ExperimentalGridApi
    interface SimpleGridCells : ExtendedGridCells, com.cheonjaeung.compose.grid.SimpleGridCells {
        /**
         * Make grid to have rows or columns with individually defined sizes, allowing a mix of
         * fixed and weighted sizes.
         *
         * For example, `Track(listOf(GridTrack.Fixed(100.dp), GridTrack.Weight(1f)))` for
         * `VerticalGrid(Modifier.width(300.dp))` means that there will be 2 columns.
         * The first column will have 100dp width and the second column will have remaining width
         * (200dp).
         *
         * @param tracks The list of tracks.
         * @param fill When `true`, item composable fill cell's width or height.
         */
        @ExperimentalGridApi
        class Track(
            private val tracks: List<GridTrack>,
            private val fill: Boolean = true
        ) : SimpleGridCells {
            constructor(vararg tracks: GridTrack, fill: Boolean = true) : this(tracks.toList(), fill)

            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                if (availableSize <= 0 || tracks.isEmpty()) {
                    return emptyList()
                }

                val totalSpacing = spacing * (tracks.size - 1)

                var totalFixedSize = 0
                var totalWeight = 0f
                for (track in tracks) {
                    when (track) {
                        is GridTrack.Fixed -> {
                            totalFixedSize += track.size.roundToPx()
                        }

                        is GridTrack.Weight -> {
                            totalWeight += track.weight
                        }
                    }
                }

                val spaceForWeight = max(0, availableSize - totalSpacing - totalFixedSize)

                val cellSizes = MutableList(tracks.size) { 0 }
                var accumulatedWeight = 0f

                for ((index, track) in tracks.withIndex()) {
                    when (track) {
                        is GridTrack.Fixed -> {
                            cellSizes[index] = track.size.roundToPx()
                        }

                        is GridTrack.Weight -> {
                            cellSizes[index] = if (totalWeight > 0) {
                                val weight = track.weight
                                val prevWeightSize =
                                    (spaceForWeight * (accumulatedWeight / totalWeight)).roundToInt()

                                accumulatedWeight += weight
                                val currentWeightSize =
                                    (spaceForWeight * (accumulatedWeight / totalWeight)).roundToInt()

                                currentWeightSize - prevWeightSize
                            } else {
                                0
                            }
                        }
                    }
                }

                return cellSizes
            }

            override fun fillCellSize(): Boolean {
                return fill
            }

            override fun equals(other: Any?): Boolean {
                if (other !is Track) return false
                if (this.tracks != other.tracks) return false
                if (this.fill != other.fill) return false
                return true
            }

            override fun hashCode(): Int {
                var hash = 1
                hash = hash * 31 + tracks.hashCode()
                hash = hash * 31 + fill.hashCode()
                return hash
            }
        }

        /**
         * Make grid to switch cell management strategy based on layout's available size.
         *
         * For example, `Responsive { if (it > 600.dp) Fixed(3) else Adaptive(120.dp) }` means that
         * the grid will have 3 columns when the available size is greater than 600dp. Otherwise,
         * it will have as many columns as possible where each cell has at least 120dp width.
         *
         * @param fill When `true`, item composable fill cell's width or height.
         * @param factory The factory lambda that determines the cell management strategy based on
         * the available size.
         */
        @ExperimentalGridApi
        class Responsive(
            private val fill: Boolean = true,
            private val factory: Density.(availableSize: Dp) -> com.cheonjaeung.compose.grid.SimpleGridCells,
        ) : SimpleGridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                if (availableSize <= 0) {
                    return emptyList()
                }

                val availableSizeDp = availableSize.toDp()
                val cells = factory(availableSizeDp)

                return with(cells) {
                    calculateCrossAxisCellSizes(availableSize, spacing)
                }
            }

            override fun fillCellSize(): Boolean {
                return fill
            }

            override fun equals(other: Any?): Boolean {
                if (other !is Responsive) return false
                if (this.fill != other.fill) return false
                if (this.factory != other.factory) return false
                return true
            }

            override fun hashCode(): Int {
                var hash = 1
                hash = hash * 31 + fill.hashCode()
                hash = hash * 31 + factory.hashCode()
                return hash
            }
        }
    }
}
