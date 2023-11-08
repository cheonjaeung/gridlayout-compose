/*
 * Copyright 2023 Jaewoong Cheon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.woong.compose.grid

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

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
            val totalSpacing = spacing * (count - 1)
            val totalCellSize = availableSize - totalSpacing
            val cellSize = totalCellSize / count
            val remainingPixels = cellSize % count
            return List(count) { index ->
                cellSize + if (remainingPixels > 0 && index == 0) 1 else 0
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
            val minSizePx = minSize.roundToPx()
            val count = max((availableSize + spacing) / (minSizePx + spacing), 1)
            val totalSpacing = spacing * (count - 1)
            val totalCellSize = availableSize - totalSpacing
            val cellSize = totalCellSize / count
            val remainingPixels = cellSize % count
            return List(count) { index ->
                cellSize + if (remainingPixels > 0 && index == 0) 1 else 0
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
}
