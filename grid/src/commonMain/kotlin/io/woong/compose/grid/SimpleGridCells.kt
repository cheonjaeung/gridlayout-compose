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

    /**
     * Make grid to have fixed number of rows or columns.
     *
     * For example, `Fixed(3)` means that there are 3 rows and columns. And each cells will have
     * same size. If grid layout size is 90dp, each cell size will be 30dp.
     *
     * @param count The number of rows or columns.
     */
    class Fixed(private val count: Int) : SimpleGridCells {
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
            return List(count) { cellSize }
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Fixed) return false
            if (this.count != other.count) return false
            return true
        }

        override fun hashCode(): Int {
            return count.hashCode()
        }
    }
}
