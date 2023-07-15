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

internal class GridMeasureResult(
    val mainAxisCount: Int,
    val crossAxisCount: Int,
    val mainAxisSize: Int,
    val crossAxisSize: Int,
)

internal class GridPositionResult(
    val mainAxisPositions: IntArray,
    val crossAxisPositions: IntArray,
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
     * Measures required grid layout size and children composable constraints.
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
        val mainAxisCount = if (measurableCount % crossAxisCount == 0) {
            measurableCount / crossAxisCount
        } else {
            measurableCount / crossAxisCount + 1
        }

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
        val mainAxisLayoutSize = max(mainAxisTotalSize, constraints.mainAxisMinSize)
        val crossAxisLayoutSize = max(crossAxisTotalSize, constraints.crossAxisMinSize)

        GridMeasureResult(
            mainAxisSize = mainAxisLayoutSize,
            crossAxisSize = crossAxisLayoutSize,
            mainAxisCount = mainAxisCount,
            crossAxisCount = crossAxisCount,
        )
    }

    /**
     * Calculates positions where the children composable should be placed.
     */
    fun position(
        measureScope: MeasureScope,
        measureResult: GridMeasureResult,
    ): GridPositionResult = with(measureScope) {
        val mainAxisCount = measureResult.mainAxisCount
        val crossAxisCount = measureResult.crossAxisCount
        val mainAxisLayoutSize = measureResult.mainAxisSize
        val crossAxisLayoutSize = measureResult.crossAxisSize
        val mainAxisPositions = IntArray(mainAxisCount) { 0 }
        val crossAxisPositions = IntArray(crossAxisCount) { 0 }

        val mainAxisBiggestChildrenSizes = IntArray(mainAxisCount) { 0 }
        for (m in 0 until mainAxisCount) {
            val currentLineChildrenSizes = IntArray(crossAxisCount) { index ->
                val placeable = placeables.getAt(m, index)
                placeable.crossAxisSize()
            }
            mainAxisBiggestChildrenSizes[m] = currentLineChildrenSizes.maxOrZero()
        }
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
                placeable.mainAxisSize()
            }
            crossAxisBiggestChildrenSizes[c] = currentLineChildrenSizes.maxOrZero()
        }
        crossAxisArrangement(
            crossAxisLayoutSize,
            crossAxisBiggestChildrenSizes,
            this.layoutDirection,
            this,
            crossAxisPositions,
        )

        GridPositionResult(
            mainAxisPositions = mainAxisPositions,
            crossAxisPositions = crossAxisPositions,
        )
    }

    /**
     * Places children composable at the correct position.
     */
    fun place(
        placeableScope: Placeable.PlacementScope,
        measureResult: GridMeasureResult,
        positionResult: GridPositionResult,
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
                            x = positionResult.mainAxisPositions[m],
                            y = positionResult.crossAxisPositions[c],
                        )
                    } else {
                        placeable.place(
                            x = positionResult.crossAxisPositions[c],
                            y = positionResult.mainAxisPositions[m],
                        )
                    }
                    i++
                }
            }
        }
    }

    private fun Placeable.mainAxisSize(): Int {
        return if (orientation == LayoutOrientation.Horizontal) {
            width
        } else {
            height
        }
    }

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
