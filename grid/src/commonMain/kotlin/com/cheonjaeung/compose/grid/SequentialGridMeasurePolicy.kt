package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
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

internal fun sequentialGridMeasurePolicy(
    orientation: LayoutOrientation,
    calculateCrossAxisCellConstraints: Density.(Constraints) -> List<Int>,
    fillCellSize: Boolean,
    mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    mainAxisSpacing: Dp,
    crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    crossAxisSpacing: Dp,
    defaultAlignment: Alignment
): MeasurePolicy {
    return SequentialGridMeasurePolicy(
        orientation,
        calculateCrossAxisCellConstraints,
        fillCellSize,
        mainAxisArrangement,
        mainAxisSpacing,
        crossAxisArrangement,
        crossAxisSpacing,
        defaultAlignment
    )
}

private class SequentialGridMeasurePolicy(
    private val orientation: LayoutOrientation,
    private val calculateCrossAxisCellConstraints: Density.(Constraints) -> List<Int>,
    private val fillCellSize: Boolean,
    private val mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    private val mainAxisSpacing: Dp,
    private val crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    private val crossAxisSpacing: Dp,
    private val defaultAlignment: Alignment
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        val crossAxisCellConstraintsList = calculateCrossAxisCellConstraints(constraints)
        val crossAxisCellCount = crossAxisCellConstraintsList.size

        val measureHelper = SequentialGridMeasureHelper(
            orientation = orientation,
            measurables = measurables,
            crossAxisCellConstraintsList = crossAxisCellConstraintsList,
            fillCellSize = fillCellSize,
            crossAxisCount = crossAxisCellCount,
            mainAxisArrangement = mainAxisArrangement,
            mainAxisSpacing = mainAxisSpacing,
            crossAxisArrangement = crossAxisArrangement,
            crossAxisSpacing = crossAxisSpacing,
            defaultAlignment = defaultAlignment
        )
        val measureResult = measureHelper.measure(
            measureScope = this,
            constraints = constraints,
        )
        val arrangeResult = measureHelper.arrange(
            measureScope = this,
            measureResult = measureResult,
        )

        val layoutWidth: Int
        val layoutHeight: Int
        when (orientation) {
            LayoutOrientation.Horizontal -> {
                layoutWidth = arrangeResult.mainAxisLayoutSize
                layoutHeight = arrangeResult.crossAxisLayoutSize
            }

            LayoutOrientation.Vertical -> {
                layoutWidth = arrangeResult.crossAxisLayoutSize
                layoutHeight = arrangeResult.mainAxisLayoutSize
            }
        }

        return layout(width = layoutWidth, height = layoutHeight) {
            measureHelper.place(
                placeableScope = this,
                arrangeResult = arrangeResult,
            )
        }
    }
}

/**
 * A class to help horizontal/vertical grid layout measuring and placing.
 */
private class SequentialGridMeasureHelper(
    val orientation: LayoutOrientation,
    val measurables: List<Measurable>,
    val crossAxisCellConstraintsList: List<Int>,
    val fillCellSize: Boolean,
    val crossAxisCount: Int,
    val mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val mainAxisSpacing: Dp,
    val crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    val crossAxisSpacing: Dp,
    val defaultAlignment: Alignment
) {
    private val gridParentDataArrays: Array<SequentialGridParentData?> = Array(measurables.size) {
        measurables[it].parentData as? SequentialGridParentData
    }

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
            val mainAxisMaxLayoutSize = constraints.mainAxisMaxSize

            var spanSum = 0
            var placeableMainAxisSizeMax = 0
            var crossAxisIndex = 0
            var crossAxisPlacedSpace = 0
            var crossAxisSpaceAfterLast: Int
            var crossAxisLineLayoutSize = 0

            while (spanSum < maxSpan && measurableIndex < measurableCount) {
                val spanScope = GridItemSpanScopeImpl(
                    maxCurrentLineSpan = maxSpan - spanSum,
                    maxLineSpan = maxSpan
                )
                val spanFunction = gridParentDataArrays[measurableIndex]?.span
                val span = if (spanFunction != null) {
                    with(spanScope) { spanFunction() }
                } else {
                    1
                }
                require(span > 0) { "span must be bigger than zero, $span is zero or negative" }

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
                        alignment = gridParentDataArrays[measurableIndex]?.alignment ?: defaultAlignment,
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
                    placeable.mainAxisSize(orientation)
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
        val mainAxisPositions = intArrayOfZeros(mainAxisCount)
        val crossAxisPositions = intArrayOfZeros(crossAxisCount)

        val mainAxisBiggestChildrenSizes = intArrayOfZeros(mainAxisCount)
        for (m in 0 until mainAxisCount) {
            val currentLinePlaceables = placeableMeasureInfoTable[m]
            val currentLineChildrenSizes = IntArray(currentLinePlaceables.size) { index ->
                val placeable = currentLinePlaceables[index].placeable
                placeable.mainAxisSize(orientation)
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
                val alignedOffset = alignment.align(
                    size = placeable.size(),
                    space = cellSize,
                    layoutDirection = this.layoutDirection
                )

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
     * Result data of the measurement for the layout and children.
     */
    class GridMeasureResult(
        val mainAxisCount: Int,
        val crossAxisCount: Int,
        val mainAxisLayoutSize: Int,
        val crossAxisLayoutSize: Int,
        val placeableMeasureInfoTable: List<List<PlaceableMeasureInfo>>
    )

    /**
     * Result data of measurement for an item placeable.
     */
    class PlaceableMeasureInfo(
        val placeable: Placeable,
        val span: Int,
        val alignment: Alignment,
        val crossAxisCellSize: Int
    )

    /**
     * Result data of the arrangement for the layout and children.
     */
    class GridArrangeResult(
        val mainAxisLayoutSize: Int,
        val crossAxisLayoutSize: Int,
        val placeablePositionInfoTable: List<List<PlaceablePositionInfo>>
    )

    /**
     * A wrapper class for [Placeable] with positions on the layout.
     */
    class PlaceablePositionInfo(
        val placeable: Placeable,
        val mainAxisPosition: Int,
        val crossAxisPosition: Int,
        val alignedOffset: IntOffset
    )
}
