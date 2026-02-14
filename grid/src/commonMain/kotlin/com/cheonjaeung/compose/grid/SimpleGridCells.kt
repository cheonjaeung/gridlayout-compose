package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A class determines the sizes and the number of cells, columns in vertical or rows in
 * horizontal grid.
 */
@Stable
interface SimpleGridCells {
    /**
     * Calculates cross axis sizes of cells based on [availableSize] and [spacing].
     *
     * @param availableSize The cross axis size of grid layout. Height in vertical or width
     * in horizontal.
     * @param spacing The cross axis spacing size passed as parameter of
     * [Arrangement][androidx.compose.foundation.layout.Arrangement]. Horizontal spacing in
     * vertical or vertical spacing in horizontal.
     */
    fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int>

    fun fillCellSize(): Boolean

    /**
     * Make grid to have fixed number of rows or columns.
     *
     * For example, `Fixed(3)` for `VerticalGrid(Modifier.width(90.dp)` means that there will be
     * 3 columns and each cell have 30dp width.
     *
     * @param count The number of rows or columns.
     * @param fill When `true`, item composable fill cell's width or height.
     */
    class Fixed(
        private val count: Int,
        private val fill: Boolean = true
    ) : SimpleGridCells {
        init {
            if (count <= 0) {
                throw IllegalArgumentException("Fixed count must be a positive value, but $count")
            }
        }

        override fun Density.calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            if (availableSize <= 0) {
                return emptyList()
            }

            val totalSpacing = spacing * (count - 1)
            val totalCellSize = availableSize - totalSpacing
            if (totalCellSize <= 0) {
                return emptyList()
            }

            val cellSize = totalCellSize / count
            val remainingPixels = totalCellSize % count
            return List(count) { index ->
                cellSize + if (index < remainingPixels) 1 else 0
            }
        }

        override fun fillCellSize(): Boolean {
            return fill
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Fixed) return false
            if (this.count != other.count) return false
            if (this.fill != other.fill) return false
            return true
        }

        override fun hashCode(): Int {
            var hash = 1
            hash = hash * 31 + count.hashCode()
            hash = hash * 31 + fill.hashCode()
            return hash
        }
    }

    /**
     * Make grid to have as many rows or columns as possible and each cell has at least [minSize].
     *
     * For example, `Adaptive(20.dp)` for `VerticalGrid(Modifier.width(66.dp)` means that there
     * will be 3 columns and each cell will have 22dp width. If grid width is changed to 80dp, the
     * column count will be 4 and each cell will have 20dp width.
     *
     * @param minSize The minimum size which each cell should have.
     * @param fill When `true`, item composable fill cell's width or height.
     */
    class Adaptive(
        private val minSize: Dp,
        private val fill: Boolean = true
    ) : SimpleGridCells {
        init {
            if (minSize <= 0.dp) {
                throw IllegalArgumentException("Adaptive minSize must be a positive value, but $minSize")
            }
        }

        override fun Density.calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            if (availableSize <= 0) {
                return emptyList()
            }

            val minSizePx = minSize.roundToPx()
            val minSizeWithSpacingPx = minSizePx + spacing
            val count = if (minSizeWithSpacingPx > 0) {
                max((availableSize + spacing) / minSizeWithSpacingPx, 1)
            } else {
                0
            }
            if (count == 0) {
                return emptyList()
            }

            val totalSpacing = spacing * (count - 1)
            val totalCellSize = availableSize - totalSpacing
            if (totalCellSize <= 0) {
                return emptyList()
            }

            val cellSize = totalCellSize / count
            val remainingPixels = totalCellSize % count
            return List(count) { index ->
                cellSize + if (index < remainingPixels) 1 else 0
            }
        }

        override fun fillCellSize(): Boolean {
            return fill
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Adaptive) return false
            if (this.minSize != other.minSize) return false
            if (this.fill != other.fill) return false
            return true
        }

        override fun hashCode(): Int {
            var hash = -1
            hash = hash * 31 + minSize.hashCode()
            hash = hash * 31 + fill.hashCode()
            return hash
        }
    }

    /**
     * Make grid to have as many rows or columns as possible and each cell has exactly [size].
     * If [size] is bigger than container's size, the cell will have the same size to the container.
     *
     * For example, `FixedSize(20.dp)` for `VerticalGrid(Modifier.width(66.dp)` means that there
     * will be 3 columns and each cell will have 20dp width and 6dp is remained.
     *
     * In other case, `FixedSize(150.dp)` for `VerticalGrid(Modifier.width(100.dp)` means that there
     * will be only one column and the cell will have 100dp width.
     *
     * @param size The size which each cell should have.
     * @param fill When `true`, item composable fill cell's width or height.
     */
    class FixedSize(
        private val size: Dp,
        private val fill: Boolean = true,
    ) : SimpleGridCells {
        init {
            if (size <= 0.dp) {
                throw IllegalArgumentException("FixedSize size must be a positive, but $size")
            }
        }

        override fun Density.calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            if (availableSize <= 0) {
                return emptyList()
            }

            val cellSize = size.roundToPx()
            val availableSizeWithSpacing = availableSize + spacing
            val cellSizeWithSpacing = cellSize + spacing
            if (cellSizeWithSpacing <= 0) {
                return emptyList()
            }

            return if (cellSizeWithSpacing < availableSizeWithSpacing) {
                val count = availableSizeWithSpacing / cellSizeWithSpacing
                List(count) { cellSize }
            } else {
                List(1) { availableSize }
            }
        }

        override fun fillCellSize(): Boolean {
            return fill
        }

        override fun equals(other: Any?): Boolean {
            if (other !is FixedSize) return false
            if (this.size != other.size) return false
            if (this.fill != other.fill) return false
            return true
        }

        override fun hashCode(): Int {
            var hash = 1
            hash = hash * 31 + size.hashCode()
            hash = hash * 31 + fill.hashCode()
            return hash
        }
    }

    /**
     * Make grid to have rows or columns with individually defined sizes, allowing a mix of fixed
     * and weighted sizes.
     *
     * For example, `Track(listOf(GridTrack.Fixed(100.dp), GridTrack.Weight(1f)))` for
     * `VerticalGrid(Modifier.width(300.dp))` means that there will be 2 columns.
     * The first column will have 100dp width and the second column will have remaining width (200dp).
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
                            val prevWeightSize = (spaceForWeight * (accumulatedWeight / totalWeight)).roundToInt()
                            accumulatedWeight += weight
                            val currentWeightSize = (spaceForWeight * (accumulatedWeight / totalWeight)).roundToInt()
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
     * the grid will have 3 columns when the available size is greater than 600dp. Otherwise, it will
     * have as many columns as possible where each cell has at least 120dp width.
     *
     * @param fill When `true`, item composable fill cell's width or height.
     * @param factory The factory lambda that determines the cell management strategy based on
     * the available size.
     */
    @ExperimentalGridApi
    class Responsive(
        private val fill: Boolean = true,
        private val factory: Density.(availableSize: Dp) -> SimpleGridCells,
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
