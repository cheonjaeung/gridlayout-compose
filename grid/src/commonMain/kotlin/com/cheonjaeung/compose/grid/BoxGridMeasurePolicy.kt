package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.roundToInt

internal fun boxGridMeasurePolicy(
    calculateColumnCellWidthConstraints: Density.(Constraints) -> List<Int>,
    calculateRowCellHeightConstraints: Density.(Constraints) -> List<Int>,
    fillCellWidth: Boolean,
    fillCellHeight: Boolean,
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    defaultAlignment: Alignment
): MeasurePolicy {
    return BoxGridMeasurePolicy(
        calculateColumnCellWidthConstraints = calculateColumnCellWidthConstraints,
        calculateRowCellHeightConstraints = calculateRowCellHeightConstraints,
        fillCellWidth = fillCellWidth,
        fillCellHeight = fillCellHeight,
        horizontalSpacing = horizontalSpacing,
        verticalSpacing = verticalSpacing,
        defaultAlignment = defaultAlignment
    )
}

private class BoxGridMeasurePolicy(
    private val calculateColumnCellWidthConstraints: Density.(Constraints) -> List<Int>,
    private val calculateRowCellHeightConstraints: Density.(Constraints) -> List<Int>,
    private val fillCellWidth: Boolean,
    private val fillCellHeight: Boolean,
    private val horizontalSpacing: Dp,
    private val verticalSpacing: Dp,
    private val defaultAlignment: Alignment
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        if (measurables.isEmpty()) {
            return layout(width = constraints.minWidth, height = constraints.minHeight) {}
        }

        val cellWidthConstraintList = calculateColumnCellWidthConstraints(constraints)
        val cellHeightConstraintList = calculateRowCellHeightConstraints(constraints)

        val measureHelper = BoxGridMeasureHelper(
            measurables = measurables,
            cellWidthConstraintList = cellWidthConstraintList,
            cellHeightConstraintList = cellHeightConstraintList,
            fillCellWidth = fillCellWidth,
            fillCellHeight = fillCellHeight,
            horizontalSpacing = horizontalSpacing,
            verticalSpacing = verticalSpacing,
            defaultAlignment = defaultAlignment
        )

        val measureResult = measureHelper.measure(measureScope = this, constraints = constraints)

        val arrangeResult = measureHelper.arrange(measureScope = this, measureResult = measureResult)

        val layoutSize = measureResult.layoutSize
        return layout(layoutSize.width.roundToInt(), layoutSize.height.roundToInt()) {
            measureHelper.place(placementScope = this, arrangeResult = arrangeResult)
        }
    }
}

private class BoxGridMeasureHelper(
    val measurables: List<Measurable>,
    val cellWidthConstraintList: List<Int>,
    val cellHeightConstraintList: List<Int>,
    val fillCellWidth: Boolean,
    val fillCellHeight: Boolean,
    val horizontalSpacing: Dp,
    val verticalSpacing: Dp,
    val defaultAlignment: Alignment
) {
    private val parentDataArray: Array<BoxGridParentData?> = Array(measurables.size) {
        measurables[it].parentData as? BoxGridParentData
    }

    private val BoxGridParentData?.rowOrDefault: Int
        get() = this?.row ?: 0

    private val BoxGridParentData?.columnOrDefault: Int
        get() = this?.column ?: 0

    private val BoxGridParentData?.alignmentOrDefault: Alignment
        get() = this?.alignment ?: defaultAlignment

    /**
     * Measures children composable constraints.
     */
    fun measure(
        measureScope: MeasureScope,
        constraints: Constraints
    ): GridMeasureResult = with(measureScope) {
        val rowCount = cellHeightConstraintList.size
        val columnCount = cellWidthConstraintList.size

        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        val placeableInfos = mutableListOfNulls<PlaceableMeasureInfo>(measurables.size)

        measurables.fastForEachIndexed { index, measurable ->
            val rowPosition = parentDataArray[index].rowOrDefault
            val columnPosition = parentDataArray[index].columnOrDefault
            if (rowPosition >= rowCount || columnPosition >= columnCount) {
                return@fastForEachIndexed
            }

            val spanScope = BoxGridItemSpanScopeImpl(
                maxCurrentRowSpan = rowCount - rowPosition,
                maxCurrentColumnSpan = columnCount - columnPosition,
                maxRowSpan = rowCount,
                maxColumnSpan = columnCount
            )
            val spanFunction = parentDataArray[index]?.span
            val span = if (spanFunction != null) {
                with(spanScope) { spanFunction() }
            } else {
                BoxGridItemSpan()
            }
            val (rowSpan, columnSpan) = span

            val remainingRowSpan = rowCount - rowPosition
            val remainingColumnSpan = columnCount - columnPosition
            if (rowSpan > remainingRowSpan || columnSpan > remainingColumnSpan) {
                return@fastForEachIndexed
            }

            val cellWidthConstraints = cellWidthConstraintList[columnPosition] * columnSpan +
                horizontalSpacingPx * (columnSpan - 1)
            val cellHeightConstraints = cellHeightConstraintList[rowPosition] * rowSpan +
                verticalSpacingPx * (rowSpan - 1)

            val placeableConstraints = Constraints(
                minWidth = if (fillCellWidth) cellWidthConstraints else 0,
                maxWidth = cellWidthConstraints,
                minHeight = if (fillCellHeight) cellHeightConstraints else 0,
                maxHeight = cellHeightConstraints
            )

            val placeable = measurable.measure(
                constraints = placeableConstraints
            )

            placeableInfos[index] = PlaceableMeasureInfo(
                placeable = placeable,
                rowPosition = rowPosition,
                columnPosition = columnPosition,
                cellConstraints = placeableConstraints,
                alignment = parentDataArray[index].alignmentOrDefault
            )
        }

        GridMeasureResult(
            layoutSize = Size(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat()),
            placeableMeasureInfos = placeableInfos
        )
    }

    /**
     * Calculates positions of the item composables.
     */
    fun arrange(
        measureScope: MeasureScope,
        measureResult: GridMeasureResult
    ): GridArrangeResult = with(measureScope) {
        val placeableCount = measureResult.placeableMeasureInfos.size
        val placeablePositionInfos = mutableListOfNulls<PlaceablePositionInfo?>(placeableCount)
        val rowCount = cellHeightConstraintList.size
        val columnCount = cellWidthConstraintList.size

        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        var currentX = if (layoutDirection == LayoutDirection.Rtl) {
            val horizontalSpacingCount = (columnCount - 1).coerceAtLeast(0)
            val horizontalSpacingSumPx = horizontalSpacingPx * horizontalSpacingCount
            val contentWidth = cellWidthConstraintList.sum() + horizontalSpacingSumPx
            measureResult.layoutSize.width.roundToInt() - contentWidth
        } else {
            0
        }
        val xPositions = IntArray(columnCount)
        for (i in 0 until columnCount) {
            xPositions[i] = currentX
            currentX += cellWidthConstraintList[i] + horizontalSpacingPx
        }

        var currentY = 0
        val yPositions = IntArray(rowCount)
        for (i in 0 until rowCount) {
            yPositions[i] = currentY
            currentY += cellHeightConstraintList[i] + verticalSpacingPx
        }

        measureResult.placeableMeasureInfos.fastForEachIndexed { index, placeableMeasureInfo ->
            if (placeableMeasureInfo != null) {
                val placeable = placeableMeasureInfo.placeable
                val alignment = placeableMeasureInfo.alignment
                val rowPosition = placeableMeasureInfo.rowPosition
                val columnPosition = placeableMeasureInfo.columnPosition

                val cellSize = IntSize(
                    width = placeableMeasureInfo.cellConstraints.maxWidth,
                    height = placeableMeasureInfo.cellConstraints.maxHeight
                )

                val xPosition = xPositions[if (layoutDirection == LayoutDirection.Ltr) {
                    columnPosition
                } else {
                    columnCount - columnPosition - 1
                }]
                val yPosition = yPositions[rowPosition]

                val alignedOffset = alignment.align(
                    size = placeable.size(),
                    space = cellSize,
                    layoutDirection = this.layoutDirection
                )

                placeablePositionInfos[index] = PlaceablePositionInfo(
                    placeable = placeable,
                    x = xPosition + alignedOffset.x,
                    y = yPosition + alignedOffset.y
                )
            }
        }

        GridArrangeResult(placeablePositionInfos = placeablePositionInfos)
    }

    /**
     * Places the item composables with measure and arrange results.
     */
    fun place(
        placementScope: Placeable.PlacementScope,
        arrangeResult: GridArrangeResult
    ) = with(placementScope) {
        arrangeResult.placeablePositionInfos.fastForEach { placeableInfo ->
            placeableInfo?.placeable?.place(
                x = placeableInfo.x,
                y = placeableInfo.y
            )
        }
    }

    class GridMeasureResult(
        val layoutSize: Size,
        val placeableMeasureInfos: List<PlaceableMeasureInfo?>
    )

    class PlaceableMeasureInfo(
        val placeable: Placeable,
        val rowPosition: Int,
        val columnPosition: Int,
        val cellConstraints: Constraints,
        val alignment: Alignment
    )

    class GridArrangeResult(
        val placeablePositionInfos: List<PlaceablePositionInfo?>
    )

    class PlaceablePositionInfo(
        val placeable: Placeable,
        val x: Int,
        val y: Int
    )
}
