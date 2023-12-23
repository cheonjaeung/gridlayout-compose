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
import androidx.compose.ui.util.fastForEach
import kotlin.math.max
import kotlin.math.min

/**
 * Result data of [GridMeasureHelper.measure].
 *
 * It contains basic layout information and placeables as 2-dimension table.
 */
internal class GridMeasureResult(
    val constraints: OrientationIndependentConstraints,
    val mainAxisCount: Int,
    val crossAxisCount: Int,
    val preArrangementMainAxisLayoutSize: Int,
    val preArrangementCrossAxisLayoutSize: Int,
    val placeableSpanInfoTable: List<List<PlaceableSpanInfo>>
)

internal class PlaceableSpanInfo(
    val placeable: Placeable,
    val span: Int
)

/**
 * Result data of [GridMeasureHelper.arrange].
 *
 * It contains layout size info and position of placeables.
 */
internal class GridArrangeResult(
    val mainAxisLayoutSize: Int,
    val crossAxisLayoutSize: Int,
    val placeablePositionInfoTable: List<List<PlaceablePositionInfo>>
)

/**
 * A wrapper class for [Placeable] with positions on the layout.
 */
internal class PlaceablePositionInfo(
    val placeable: Placeable,
    val mainAxisPosition: Int,
    val crossAxisPosition: Int
)

/**
 * A class to help grid layout measuring and placing.
 */
internal class GridMeasureHelper(
    val orientation: LayoutOrientation,
    val measurables: List<Measurable>,
    val crossAxisCellConstraintsList: List<Int>,
    val fillCellSize: Boolean,
    val crossAxisCount: Int,
    val mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val mainAxisSpacing: Dp,
    val crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val crossAxisSpacing: Dp,
) {
    private val gridSpanParentDataArray: Array<GridSpanParentData?> = Array(measurables.size) {
        measurables[it].parentData as? GridSpanParentData
    }

    private val GridSpanParentData?.spanOrDefault: Int
        get() = this?.span ?: GridSpanParentData.DefaultSpan

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
        val placeableTable = mutableListOf<List<PlaceableSpanInfo>>()
        val mainAxisSpacingPx = mainAxisSpacing.roundToPx()
        val crossAxisSpacingPx = crossAxisSpacing.roundToPx()
        val measurableCount = measurables.size
        val maxSpan = crossAxisCount

        var measurableIndex = 0
        var mainAxisIndex = 0
        var mainAxisPlacedSpace = 0
        var mainAxisSpaceAfterLast: Int
        var mainAxisTotalLayoutSize = 0
        var crossAxisTotalLayoutSize = 0

        while (measurableIndex < measurableCount) {
            var spanSum = 0
            val mainAxisMaxLayoutSize = constraints.mainAxisMaxSize
            var crossAxisIndex = 0
            var placeableMainAxisSizeMax = 0
            var crossAxisPlacedSpace = 0
            var crossAxisSpaceAfterLast: Int
            val placeableLine = mutableListOf<PlaceableSpanInfo>()

            while (spanSum < maxSpan && measurableIndex < measurableCount) {
                val span = gridSpanParentDataArray[measurableIndex].spanOrDefault
                if (span > maxSpan) {
                    measurableIndex++
                    continue
                }
                val remainingSpan = maxSpan - spanSum
                if (span > remainingSpan) {
                    break
                }
                spanSum += span

                val crossAxisMaxLayoutSize = constraints.crossAxisMaxSize
                val measurable = measurables[measurableIndex]
                val crossAxisCellConstraints = crossAxisCellConstraintsList[crossAxisIndex] * span +
                    crossAxisSpacingPx * (span - 1)

                val placeable = measurable.measure(
                    constraints = OrientationIndependentConstraints(
                        mainAxisMinSize = 0,
                        mainAxisMaxSize = if (mainAxisMaxLayoutSize == Constraints.Infinity) {
                            Constraints.Infinity
                        } else {
                            mainAxisMaxLayoutSize - mainAxisPlacedSpace
                        },
                        crossAxisMinSize = if (fillCellSize) crossAxisCellConstraints else 0,
                        crossAxisMaxSize = crossAxisCellConstraints,
                    ).toConstraints(orientation)
                )
                placeableLine.add(
                    PlaceableSpanInfo(
                        placeable = placeable,
                        span = span
                    )
                )

                crossAxisSpaceAfterLast = min(
                    crossAxisSpacingPx,
                    crossAxisMaxLayoutSize - crossAxisPlacedSpace - crossAxisCellConstraints
                )
                crossAxisPlacedSpace += crossAxisCellConstraints + crossAxisSpaceAfterLast
                placeableMainAxisSizeMax = max(
                    placeableMainAxisSizeMax,
                    placeable.mainAxisSize()
                )
                crossAxisTotalLayoutSize = max(crossAxisTotalLayoutSize, crossAxisPlacedSpace)
                crossAxisIndex += span
                measurableIndex++
            }
            crossAxisTotalLayoutSize -= crossAxisSpacingPx

            placeableTable.add(placeableLine)

            mainAxisSpaceAfterLast = min(
                mainAxisSpacingPx,
                mainAxisMaxLayoutSize - mainAxisPlacedSpace - placeableMainAxisSizeMax
            )
            mainAxisPlacedSpace += placeableMainAxisSizeMax + mainAxisSpaceAfterLast
            mainAxisTotalLayoutSize = max(mainAxisTotalLayoutSize, mainAxisPlacedSpace)
            mainAxisIndex++
        }
        mainAxisTotalLayoutSize -= mainAxisSpacingPx

        val mainAxisLayoutSizeBeforeArrange = mainAxisTotalLayoutSize.coerceIn(
            constraints.mainAxisMinSize,
            constraints.mainAxisMaxSize
        )
        val crossAxisLayoutSizeBeforeArrange = crossAxisTotalLayoutSize.coerceIn(
            constraints.crossAxisMinSize,
            constraints.crossAxisMaxSize
        )

        GridMeasureResult(
            constraints = constraints,
            mainAxisCount = mainAxisIndex,
            crossAxisCount = crossAxisCount,
            preArrangementMainAxisLayoutSize = mainAxisLayoutSizeBeforeArrange,
            preArrangementCrossAxisLayoutSize = crossAxisLayoutSizeBeforeArrange,
            placeableSpanInfoTable = placeableTable
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
        val placeableSpanInfoTable = measureResult.placeableSpanInfoTable
        val mainAxisCount = measureResult.mainAxisCount
        val crossAxisCount = measureResult.crossAxisCount
        val preArrangementMainAxisLayoutSize = measureResult.preArrangementMainAxisLayoutSize
        val preArrangementCrossAxisLayoutSize = measureResult.preArrangementCrossAxisLayoutSize
        val mainAxisPositions = IntArray(mainAxisCount) { 0 }
        val crossAxisPositions = IntArray(crossAxisCount) { 0 }

        val mainAxisBiggestChildrenSizes = IntArray(mainAxisCount) { 0 }
        for (m in 0 until mainAxisCount) {
            val currentLinePlaceables = placeableSpanInfoTable[m]
            val currentLineChildrenSizes = IntArray(currentLinePlaceables.size) { index ->
                val placeable = currentLinePlaceables[index].placeable
                placeable.mainAxisSize()
            }
            mainAxisBiggestChildrenSizes[m] = currentLineChildrenSizes.maxOrZero()
        }
        val mainAxisLayoutSize = max(
            mainAxisBiggestChildrenSizes.sum(),
            preArrangementMainAxisLayoutSize
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

        val crossAxisLayoutSize = max(
            crossAxisCellConstraintsList.sum(),
            preArrangementCrossAxisLayoutSize
        ).coerceIn(
            constraints.crossAxisMinSize,
            constraints.crossAxisMaxSize
        )
        crossAxisArrangement(
            crossAxisLayoutSize,
            crossAxisCellConstraintsList.toIntArray(),
            this.layoutDirection,
            this,
            crossAxisPositions,
        )

        val placeableInfoTable = mutableListOf<List<PlaceablePositionInfo>>()
        var mainAxisIndex = 0
        placeableSpanInfoTable.fastForEach { placeableLine ->
            val placeableInfoLine = mutableListOf<PlaceablePositionInfo>()
            var crossAxisIndex = 0
            placeableLine.fastForEach { placeableSpanInfo ->
                placeableInfoLine.add(
                    PlaceablePositionInfo(
                        placeable = placeableSpanInfo.placeable,
                        mainAxisPosition = mainAxisPositions[mainAxisIndex],
                        crossAxisPosition = crossAxisPositions[crossAxisIndex]
                    )
                )
                crossAxisIndex += placeableSpanInfo.span
            }
            placeableInfoTable.add(placeableInfoLine)
            mainAxisIndex++
        }

        GridArrangeResult(
            mainAxisLayoutSize = mainAxisLayoutSize,
            crossAxisLayoutSize = crossAxisLayoutSize,
            placeablePositionInfoTable = placeableInfoTable
        )
    }

    /**
     * Places the layout children composable using measuring and arrangement results.
     */
    fun place(
        placeableScope: Placeable.PlacementScope,
        arrangeResult: GridArrangeResult,
    ) = with(placeableScope) {
        val placeableInfoTable = arrangeResult.placeablePositionInfoTable
        placeableInfoTable.fastForEach { placeableInfoLine ->
            placeableInfoLine.fastForEach { placeableInfo ->
                val placeable = placeableInfo.placeable
                if (orientation == LayoutOrientation.Horizontal) {
                    placeable.place(
                        x = placeableInfo.mainAxisPosition,
                        y = placeableInfo.crossAxisPosition
                    )
                } else {
                    placeable.place(
                        x = placeableInfo.crossAxisPosition,
                        y = placeableInfo.mainAxisPosition
                    )
                }
            }
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
