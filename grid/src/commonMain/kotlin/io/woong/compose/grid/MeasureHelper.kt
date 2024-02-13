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

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.max
import kotlin.math.min

/**
 * Result data of [GridMeasureHelper.measure].
 *
 * It contains basic layout information and placeables as 2-dimension table.
 */
internal class GridMeasureResult(
    val mainAxisCount: Int,
    val crossAxisCount: Int,
    val mainAxisLayoutSize: Int,
    val crossAxisLayoutSize: Int,
    val placeableMeasureInfoTable: List<List<PlaceableMeasureInfo>>
)

internal class PlaceableMeasureInfo(
    val placeable: Placeable,
    val span: Int,
    val alignment: Alignment?,
    val crossAxisCellSize: Int
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
    val crossAxisPosition: Int,
    val alignedOffset: IntOffset
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
    private val gridParentDataArrays: Array<GridParentData?> = Array(measurables.size) {
        measurables[it].parentData as? GridParentData
    }

    private val GridParentData?.spanOrDefault: Int
        get() = this?.span ?: GridParentData.DefaultSpan

    /**
     * Measures children composable constraints.
     */
    fun measure(
        measureScope: MeasureScope,
        constraints: Constraints,
    ): GridMeasureResult = with(measureScope) {
        @Suppress("NAME_SHADOWING")
        val constraints = OrientationIndependentConstraints(orientation, constraints)
        val placeableTable = mutableListOf<List<PlaceableMeasureInfo>>()
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
            val placeableLine = mutableListOf<PlaceableMeasureInfo>()
            var spanSum = 0
            val mainAxisMaxLayoutSize = constraints.mainAxisMaxSize
            var crossAxisIndex = 0
            var placeableMainAxisSizeMax = 0
            var crossAxisPlacedSpace = 0
            var crossAxisSpaceAfterLast: Int
            var crossAxisLineLayoutSize = 0

            while (spanSum < maxSpan && measurableIndex < measurableCount) {
                val span = gridParentDataArrays[measurableIndex].spanOrDefault
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
                            (mainAxisMaxLayoutSize - mainAxisPlacedSpace).coerceAtLeast(0)
                        },
                        crossAxisMinSize = if (fillCellSize) crossAxisCellConstraints else 0,
                        crossAxisMaxSize = crossAxisCellConstraints,
                    ).toConstraints(orientation)
                )
                placeableLine.add(
                    PlaceableMeasureInfo(
                        placeable = placeable,
                        span = span,
                        alignment = gridParentDataArrays[measurableIndex]?.alignment,
                        crossAxisCellSize = crossAxisCellConstraints
                    )
                )

                crossAxisSpaceAfterLast = min(
                    crossAxisSpacingPx,
                    crossAxisMaxLayoutSize + crossAxisSpacingPx - crossAxisPlacedSpace - crossAxisCellConstraints
                )
                crossAxisPlacedSpace += crossAxisCellConstraints + crossAxisSpaceAfterLast
                placeableMainAxisSizeMax = max(
                    placeableMainAxisSizeMax,
                    placeable.mainAxisSize()
                )
                crossAxisLineLayoutSize = max(crossAxisLineLayoutSize, crossAxisPlacedSpace)
                crossAxisIndex += span
                measurableIndex++
            }
            crossAxisLineLayoutSize -= crossAxisSpacingPx
            crossAxisTotalLayoutSize = max(crossAxisTotalLayoutSize, crossAxisLineLayoutSize)

            placeableTable.add(placeableLine)

            mainAxisSpaceAfterLast = min(
                mainAxisSpacingPx,
                mainAxisMaxLayoutSize + mainAxisSpacingPx - mainAxisPlacedSpace - placeableMainAxisSizeMax
            )
            mainAxisPlacedSpace += placeableMainAxisSizeMax + mainAxisSpaceAfterLast
            mainAxisTotalLayoutSize = max(mainAxisTotalLayoutSize, mainAxisPlacedSpace)
            mainAxisIndex++
        }
        mainAxisTotalLayoutSize -= mainAxisSpacingPx

        val mainAxisLayoutSize = mainAxisTotalLayoutSize.coerceIn(
            constraints.mainAxisMinSize,
            constraints.mainAxisMaxSize
        )
        val crossAxisLayoutSize = crossAxisTotalLayoutSize.coerceIn(
            constraints.crossAxisMinSize,
            constraints.crossAxisMaxSize
        )

        GridMeasureResult(
            mainAxisCount = mainAxisIndex,
            crossAxisCount = crossAxisCount,
            mainAxisLayoutSize = mainAxisLayoutSize,
            crossAxisLayoutSize = crossAxisLayoutSize,
            placeableMeasureInfoTable = placeableTable
        )
    }

    /**
     * Calculates positions of the item composables.
     */
    fun arrange(
        measureScope: MeasureScope,
        measureResult: GridMeasureResult,
    ): GridArrangeResult = with(measureScope) {
        val placeableMeasureInfoTable = measureResult.placeableMeasureInfoTable
        val mainAxisCount = measureResult.mainAxisCount
        val crossAxisCount = measureResult.crossAxisCount
        val mainAxisLayoutSize = measureResult.mainAxisLayoutSize
        val crossAxisLayoutSize = measureResult.crossAxisLayoutSize
        val mainAxisPositions = IntArray(mainAxisCount) { 0 }
        val crossAxisPositions = IntArray(crossAxisCount) { 0 }

        val mainAxisBiggestChildrenSizes = IntArray(mainAxisCount) { 0 }
        for (m in 0 until mainAxisCount) {
            val currentLinePlaceables = placeableMeasureInfoTable[m]
            val currentLineChildrenSizes = IntArray(currentLinePlaceables.size) { index ->
                val placeable = currentLinePlaceables[index].placeable
                placeable.mainAxisSize()
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

        crossAxisArrangement(
            crossAxisLayoutSize,
            crossAxisCellConstraintsList.toIntArray(),
            this.layoutDirection,
            this,
            crossAxisPositions,
        )

        val placeableInfoTable = mutableListOf<List<PlaceablePositionInfo>>()
        placeableMeasureInfoTable.fastForEachIndexed { mainAxisIndex, placeableLine ->
            val placeableInfoLine = mutableListOf<PlaceablePositionInfo>()
            var crossAxisIndex = 0
            val currentLineMainAxisCellSize = mainAxisBiggestChildrenSizes[mainAxisIndex]
            placeableLine.fastForEach { placeableMeasureInfo ->
                val placeable = placeableMeasureInfo.placeable
                val alignment = placeableMeasureInfo.alignment
                val cellSize = if (orientation == LayoutOrientation.Horizontal) {
                    IntSize(
                        width = currentLineMainAxisCellSize,
                        height = placeableMeasureInfo.crossAxisCellSize
                    )
                } else {
                    IntSize(
                        width = placeableMeasureInfo.crossAxisCellSize,
                        height = currentLineMainAxisCellSize
                    )
                }
                val alignedOffset = alignment?.align(
                    size = placeable.size(),
                    space = cellSize,
                    layoutDirection = this.layoutDirection
                ) ?: IntOffset.Zero

                placeableInfoLine.add(
                    PlaceablePositionInfo(
                        placeable = placeable,
                        mainAxisPosition = mainAxisPositions[mainAxisIndex],
                        crossAxisPosition = crossAxisPositions[crossAxisIndex],
                        alignedOffset = alignedOffset
                    )
                )
                crossAxisIndex += placeableMeasureInfo.span
            }
            placeableInfoTable.add(placeableInfoLine)
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
                val offset = placeableInfo.alignedOffset

                if (orientation == LayoutOrientation.Horizontal) {
                    placeable.place(
                        x = placeableInfo.mainAxisPosition + offset.x,
                        y = placeableInfo.crossAxisPosition + offset.y
                    )
                } else {
                    placeable.place(
                        x = placeableInfo.crossAxisPosition + offset.x,
                        y = placeableInfo.mainAxisPosition + offset.y
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
     * Returns the size of this [Placeable].
     */
    private fun Placeable.size(): IntSize {
        return IntSize(width = width, height = height)
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
