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
import androidx.compose.ui.unit.Constraints
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
     * Calculates cross axis minimum sizes of cells based on [availableSize] and [spacing].
     *
     * @param availableSize The cross axis size of grid layout. Height in vertical or width
     * in horizontal.
     * @param spacing The cross axis spacing size passed as parameter of
     * [Arrangement][androidx.compose.foundation.layout.Arrangement]. Horizontal spacing in
     * vertical or vertical spacing in horizontal.
     */
    fun Density.calculateCrossAxisMinCellSizes(availableSize: Int, spacing: Int): List<Int>

    /**
     * Calculates cross axis maximum sizes of cells based on [availableSize] and [spacing].
     *
     * @param availableSize The cross axis size of grid layout. Height in vertical or width
     * in horizontal.
     * @param spacing The cross axis spacing size passed as parameter of
     * [Arrangement][androidx.compose.foundation.layout.Arrangement]. Horizontal spacing in
     * vertical or vertical spacing in horizontal.
     */
    fun Density.calculateCrossAxisMaxCellSizes(availableSize: Int, spacing: Int): List<Int>

    /**
     * Make grid to have fixed number of rows or columns.
     *
     * For example, 3 for [count] means that there are 3 rows or columns. Additionally, If
     * [sameWeight] is `true`, each row or column has 1/3 size of grid layout cross axis size.
     *
     * @param count The number of rows or columns.
     * @param sameWeight Whether the row or column sizes should be same. If `true` the grid
     * layout size should be measurable, not an infinite.
     */
    class Fixed(private val count: Int, private val sameWeight: Boolean = true) : SimpleGridCells {
        init {
            if (count <= 0) {
                throw IllegalArgumentException("Fixed count must be a positive value, but $count")
            }
        }

        override fun Density.calculateCrossAxisMinCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            return if (sameWeight) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            } else {
                List(count) { 0 }
            }
        }

        override fun Density.calculateCrossAxisMaxCellSizes(
            availableSize: Int,
            spacing: Int,
        ): List<Int> {
            return if (sameWeight) {
                calculateCrossAxisCellSizes(availableSize, spacing)
            } else {
                List(count) { Constraints.Infinity }
            }
        }

        private fun calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int,
        ): List<Int> {
            val totalSpacing = spacing * (count - 1)
            val totalCellSize = availableSize - totalSpacing
            val cellSize = totalCellSize / count
            return List(count) { cellSize }
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Fixed) return false
            if (this.count != other.count) return false
            if (this.sameWeight != other.sameWeight) return false
            return true
        }

        override fun hashCode(): Int {
            var hash = count.hashCode()
            hash = 31 * hash + sameWeight.hashCode()
            return hash
        }
    }

}
