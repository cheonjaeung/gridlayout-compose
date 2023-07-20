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

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.max
import kotlin.math.min

internal data class GridMeasureResult(
    val constraints: OrientationIndependentConstraints,
    val mainAxisCount: Int,
    val crossAxisCount: Int,
    val mainAxisLayoutSize: Int,
    val crossAxisLayoutSize: Int,
)

internal class GridArrangeResult(
    val mainAxisPositions: IntArray,
    val crossAxisPositions: IntArray,
    val mainAxisLayoutSize: Int,
    val crossAxisLayoutSize: Int,
)

/**
 * A class to help grid layout measuring and placing.
 */
internal class GridMeasureHelper(
    val orientation: LayoutOrientation,
    val measurables: List<Measurable>,
    val placeables: Array<Placeable?>,
    val crossAxisCount: Int,
    val mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val mainAxisSpacing: Dp,
    val crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val crossAxisSpacing: Dp,
) {
    /**
     * Measures children composable constraints.
     *
     * This method calculates children constraints (minimum and maximum size) using layout
     * constrains and arrangement spacing. After measuring step, the placeables array elements
     * null safety is guaranteed. The result layout size of this method may be incorrect because
     * it is before the arrangement step.
     */
    fun measure(
        measureScope: MeasureScope,
        constraints: Constraints,
    ): GridMeasureResult = with(measureScope) {
        @Suppress("NAME_SHADOWING")
        val constraints = OrientationIndependentConstraints(orientation, constraints)
        val mainAxisSpacingPx = mainAxisSpacing.roundToPx()
        val crossAxisSpacingPx = crossAxisSpacing.roundToPx()
        val measurableCount = measurables.size
        val mainAxisCount = mainAxisCountByCrossAxisCount(measurableCount, crossAxisCount)

        var i = 0
        var mainAxisPlacedSpace = 0
        var mainAxisSpaceAfterLast: Int
        var mainAxisTotalSize = 0
        var crossAxisTotalSize = 0
        for (m in 0 until mainAxisCount) {
            val mainAxisMax = constraints.mainAxisMaxSize
            var placeableMainAxisSizeMax = 0
            var crossAxisPlacedSpace = 0
            var crossAxisSpaceAfterLast: Int
            for (c in 0 until crossAxisCount) {
                if (i < measurableCount) {
                    val measurable = measurables[i]
                    val crossAxisMax = constraints.crossAxisMaxSize
                    val placeable = measurable.measure(
                        constraints = OrientationIndependentConstraints(
                            mainAxisMinSize = 0,
                            mainAxisMaxSize = if (mainAxisMax == Constraints.Infinity) {
                                Constraints.Infinity
                            } else {
                                mainAxisMax - mainAxisPlacedSpace
                            },
                            crossAxisMinSize = 0,
                            crossAxisMaxSize = if (crossAxisMax == Constraints.Infinity) {
                                Constraints.Infinity
                            } else {
                                crossAxisMax - crossAxisPlacedSpace
                            },
                        ).toConstraints(orientation)
                    )
                    crossAxisSpaceAfterLast = min(
                        if (c == crossAxisCount - 1) 0 else crossAxisSpacingPx,
                        crossAxisMax - crossAxisPlacedSpace - placeable.crossAxisSize()
                    )
                    crossAxisPlacedSpace += placeable.crossAxisSize() + crossAxisSpaceAfterLast
                    placeableMainAxisSizeMax =
                        max(placeableMainAxisSizeMax, placeable.mainAxisSize())
                    crossAxisTotalSize = max(crossAxisTotalSize, crossAxisPlacedSpace)
                    placeables[i] = placeable
                    i++
                }
            }
            mainAxisSpaceAfterLast = min(
                if (m == mainAxisCount - 1) 0 else mainAxisSpacingPx,
                mainAxisMax - mainAxisPlacedSpace - placeableMainAxisSizeMax
            )
            mainAxisPlacedSpace += placeableMainAxisSizeMax + mainAxisSpaceAfterLast
            mainAxisTotalSize = max(mainAxisTotalSize, mainAxisPlacedSpace)
        }
        val mainAxisLayoutSizeBeforeArrange = mainAxisTotalSize.coerceIn(
            constraints.mainAxisMinSize,
            constraints.mainAxisMaxSize
        )
        val crossAxisLayoutSizeBeforeArrange = crossAxisTotalSize.coerceIn(
            constraints.crossAxisMinSize,
            constraints.crossAxisMaxSize
        )

        GridMeasureResult(
            constraints = constraints,
            mainAxisCount = mainAxisCount,
            crossAxisCount = crossAxisCount,
            mainAxisLayoutSize = mainAxisLayoutSizeBeforeArrange,
            crossAxisLayoutSize = crossAxisLayoutSizeBeforeArrange,
        )
    }

    /**
     * Arranges children composable and remeasures grid layout size.
     *
     * The arrangement step calculates children composable's position in the grid layout.
     * After the arrangement, this method recalculates grid layout size and the result size is
     * actual layout size.
     */
    fun arrange(
        measureScope: MeasureScope,
        measureResult: GridMeasureResult,
    ): GridArrangeResult = with(measureScope) {
        val constraints = measureResult.constraints
        val mainAxisCount = measureResult.mainAxisCount
        val crossAxisCount = measureResult.crossAxisCount
        val mainAxisLayoutSizeBeforeArrange = measureResult.mainAxisLayoutSize
        val crossAxisLayoutSizeBeforeArrange = measureResult.crossAxisLayoutSize
        val mainAxisPositions = IntArray(mainAxisCount) { 0 }
        val crossAxisPositions = IntArray(crossAxisCount) { 0 }

        val mainAxisBiggestChildrenSizes = IntArray(mainAxisCount) { 0 }
        for (m in 0 until mainAxisCount) {
            val currentLineChildrenSizes = IntArray(crossAxisCount) { index ->
                val placeable = placeables.getAt(m, index)
                placeable.mainAxisSize()
            }
            mainAxisBiggestChildrenSizes[m] = currentLineChildrenSizes.maxOrZero()
        }
        val mainAxisLayoutSize = max(
            mainAxisBiggestChildrenSizes.sum(),
            mainAxisLayoutSizeBeforeArrange
        ).coerceIn(
            constraints.mainAxisMinSize,
            constraints.mainAxisMaxSize
        )
        mainAxisArrangement(
            mainAxisLayoutSize,
            mainAxisBiggestChildrenSizes,
            this.layoutDirection,
            this,
            mainAxisPositions,
        )

        val crossAxisBiggestChildrenSizes = IntArray(crossAxisCount) { 0 }
        for (c in 0 until crossAxisCount) {
            val currentLineChildrenSizes = IntArray(mainAxisCount) { index ->
                val placeable = placeables.getAt(index, c)
                placeable.crossAxisSize()
            }
            crossAxisBiggestChildrenSizes[c] = currentLineChildrenSizes.maxOrZero()
        }
        val crossAxisLayoutSize = max(
            crossAxisBiggestChildrenSizes.sum(),
            crossAxisLayoutSizeBeforeArrange
        ).coerceIn(
            constraints.crossAxisMinSize,
            constraints.crossAxisMaxSize
        )
        crossAxisArrangement(
            crossAxisLayoutSize,
            crossAxisBiggestChildrenSizes,
            this.layoutDirection,
            this,
            crossAxisPositions,
        )

        GridArrangeResult(
            mainAxisPositions = mainAxisPositions,
            crossAxisPositions = crossAxisPositions,
            mainAxisLayoutSize = mainAxisLayoutSize,
            crossAxisLayoutSize = crossAxisLayoutSize,
        )
    }

    /**
     * Places the layout children composable using measuring and arrangement results.
     */
    fun place(
        placeableScope: Placeable.PlacementScope,
        measureResult: GridMeasureResult,
        arrangeResult: GridArrangeResult,
    ) = with(placeableScope) {
        var i = 0
        for (m in 0 until measureResult.mainAxisCount) {
            for (c in 0 until measureResult.crossAxisCount) {
                if (i < placeables.size) {
                    val placeable = placeables[i]
                    // Placeable must not null on this time.
                    placeable!!

                    if (orientation == LayoutOrientation.Horizontal) {
                        placeable.place(
                            x = arrangeResult.mainAxisPositions[m],
                            y = arrangeResult.crossAxisPositions[c],
                        )
                    } else {
                        placeable.place(
                            x = arrangeResult.crossAxisPositions[c],
                            y = arrangeResult.mainAxisPositions[m],
                        )
                    }
                    i++
                }
            }
        }
    }

    private fun mainAxisCountByCrossAxisCount(totalCount: Int, crossAxisCount: Int): Int {
        return if (totalCount % crossAxisCount == 0) {
            totalCount / crossAxisCount
        } else {
            totalCount / crossAxisCount + 1
        }
    }

    /**
     * Returns main axis size of this [Placeable] by current orientation.
     */
    private fun Placeable.mainAxisSize(): Int {
        return if (orientation == LayoutOrientation.Horizontal) {
            width
        } else {
            height
        }
    }

    /**
     * Returns cross axis size of this [Placeable] by current orientation.
     */
    private fun Placeable.crossAxisSize(): Int {
        return if (orientation == LayoutOrientation.Horizontal) {
            height
        } else {
            width
        }
    }

    /**
     * Get a null-safe [Placeable] by main-axis index and cross-axis index.
     *
     * This function must be called only where [Placeable]s null-safety is guaranteed.
     */
    private fun Array<Placeable?>.getAt(mainAxisIndex: Int, crossAxisIndex: Int): Placeable {
        val mainAxisMaxIndex = (this.size / crossAxisCount) - 1
        return this[(mainAxisMaxIndex * mainAxisIndex) + crossAxisIndex]!!
    }

    /**
     * Returns the largest element or 0 if there are no elements.
     */
    private fun IntArray.maxOrZero(): Int {
        if (this.isEmpty()) {
            return 0
        }
        var maxValue = this[0]
        for (i in 1 until this.size) {
            maxValue = max(maxValue, this[i])
        }
        return maxValue
    }
}
