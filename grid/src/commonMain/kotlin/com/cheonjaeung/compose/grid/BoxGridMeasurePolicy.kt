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
        get() = this?.row ?: BoxGridParentData.DEFAULT_ROW

    private val BoxGridParentData?.columnOrDefault: Int
        get() = this?.column ?: BoxGridParentData.DEFAULT_COLUMN

    private val BoxGridParentData?.rowSpanOrDefault: (GridItemSpanScope.() -> Int)
        get() = this?.rowSpan ?: BoxGridParentData.DefaultSpan

    private val BoxGridParentData?.columnSpanOrDefault: (GridItemSpanScope.() -> Int)
        get() = this?.columnSpan ?: BoxGridParentData.DefaultSpan

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

            val rowSpanScope = GridItemSpanScopeImpl(
                maxCurrentLineSpan = rowCount - rowPosition,
                maxLineSpan = rowCount
            )
            val columnSpanScope = GridItemSpanScopeImpl(
                maxCurrentLineSpan = columnCount - columnPosition,
                maxLineSpan = columnCount
            )
            val rowSpanFunction = parentDataArray[index].rowSpanOrDefault
            val columnSpanFunction = parentDataArray[index].columnSpanOrDefault
            val rowSpan = rowSpanFunction(rowSpanScope)
            val columnSpan = columnSpanFunction(columnSpanScope)

            require(rowSpan > 0) {
                "rowSpan must be bigger than zero, $rowSpan is zero or negative"
            }
            require(columnSpan > 0) {
                "columnSpan must be bigger than zero, $columnSpan is zero or negative"
            }
            val remainingRowSpan = rowCount - rowSpan
            val remainingColumnSpan = columnCount - columnSpan
            if (rowSpan > remainingRowSpan || columnSpan > remainingColumnSpan) {
                return@fastForEachIndexed
            }

            val cellWidthConstraints = cellWidthConstraintList[columnPosition] * columnSpan +
                verticalSpacingPx * (columnSpan - 1)
            val cellHeightConstraints = cellHeightConstraintList[rowPosition] * rowSpan +
                horizontalSpacingPx * (rowSpan - 1)

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

                val xPosition = if (layoutDirection == LayoutDirection.Ltr) {
                    val xWithoutSpacing = cellWidthConstraintList.sumOfIndexed { i, c ->
                        if (i < columnPosition) c else 0
                    }
                    xWithoutSpacing + horizontalSpacing.roundToPx() * columnPosition
                } else {
                    val layoutWidth = measureResult.layoutSize.width.roundToInt()
                    val xWithoutSpacing = layoutWidth - cellWidthConstraintList.sumOfIndexed { i, c ->
                        if (i <= columnPosition) c else 0
                    }
                    xWithoutSpacing - horizontalSpacing.roundToPx() * columnPosition
                }

                val yPosition = run {
                    val yWithoutSpacing = cellHeightConstraintList.sumOfIndexed { i, c ->
                        if (i < rowPosition) c else 0
                    }
                    yWithoutSpacing + verticalSpacing.roundToPx() * rowPosition
                }

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

    private fun <T> mutableListOfNulls(size: Int): MutableList<T?> {
        return MutableList(size) { null }
    }

    private fun Placeable.size(): IntSize {
        return IntSize(width = width, height = height)
    }

    private fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
        var sum = 0
        for ((index, element) in this.withIndex()) {
            sum += selector(index, element)
        }
        return sum
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
