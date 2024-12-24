package com.cheonjaeung.compose.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density

/**
 * A layout composable that places its children in a grid.
 *
 * @param rows The class that determines the sizes and the number of grid cells.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the grid cells.
 * @param verticalArrangement The vertical arrangement of the grid cells.
 * @param alignment The default alignment of the grid cells.
 * @param content The children of this layout.
 */
@Composable
inline fun HorizontalGrid(
    rows: SimpleGridCells,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    alignment: Alignment = Alignment.TopStart,
    content: @Composable GridScope.() -> Unit,
) {
    val calculateRowCellHeightsFunction = rememberRowCellHeightConstraints(
        rows = rows,
        verticalArrangement = verticalArrangement,
    )
    val measurePolicy = rememberHorizontalGridMeasurePolicy(
        calculateRowCellHeightConstraints = calculateRowCellHeightsFunction,
        fillCellHeight = remember(rows) { rows.fillCellSize() },
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        alignment = alignment
    )
    Layout(
        content = { GridScopeInstance.content() },
        measurePolicy = measurePolicy,
        modifier = modifier,
    )
}

/**
 * A layout composable that places its children in a grid.
 *
 * @param columns The class that determines the sizes and the number of grid cells.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the grid cells.
 * @param verticalArrangement The vertical arrangement of the grid cells.
 * @param alignment The default alignment of the grid cells.
 * @param content The children of this layout.
 */
@Composable
inline fun VerticalGrid(
    columns: SimpleGridCells,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    alignment: Alignment = Alignment.TopStart,
    content: @Composable GridScope.() -> Unit,
) {
    val calculateColumnCellWidthsFunction = rememberColumnCellWidthConstraints(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
    )
    val measurePolicy = rememberVerticalGridMeasurePolicy(
        calculateColumnCellWidthConstraints = calculateColumnCellWidthsFunction,
        fillCellWidth = remember(columns) { columns.fillCellSize() },
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        alignment = alignment
    )
    Layout(
        content = { GridScopeInstance.content() },
        measurePolicy = measurePolicy,
        modifier = modifier,
    )
}

@PublishedApi
@Composable
internal fun rememberRowCellHeightConstraints(
    rows: SimpleGridCells,
    verticalArrangement: Arrangement.Vertical,
): Density.(Constraints) -> List<Int> {
    return remember(rows, verticalArrangement) {
        { constraints ->
            val gridHeight = constraints.maxHeight
            if (gridHeight == Constraints.Infinity) {
                throw IllegalStateException(
                    "HorizontalGrid's height should be measurable, not an infinite."
                )
            }
            with(rows) {
                calculateCrossAxisCellSizes(
                    availableSize = gridHeight,
                    spacing = verticalArrangement.spacing.roundToPx(),
                )
            }
        }
    }
}

@PublishedApi
@Composable
internal fun rememberColumnCellWidthConstraints(
    columns: SimpleGridCells,
    horizontalArrangement: Arrangement.Horizontal,
): Density.(Constraints) -> List<Int> {
    return remember(columns, horizontalArrangement) {
        { constraints ->
            val gridWidth = constraints.maxWidth
            if (gridWidth == Constraints.Infinity) {
                throw IllegalStateException(
                    "VerticalGrid's width should be measurable, not an infinite."
                )
            }
            with(columns) {
                calculateCrossAxisCellSizes(
                    availableSize = gridWidth,
                    spacing = horizontalArrangement.spacing.roundToPx(),
                )
            }
        }
    }
}

@PublishedApi
@Composable
internal fun rememberHorizontalGridMeasurePolicy(
    calculateRowCellHeightConstraints: Density.(Constraints) -> List<Int>,
    fillCellHeight: Boolean,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    alignment: Alignment,
): MeasurePolicy {
    return remember(
        calculateRowCellHeightConstraints,
        fillCellHeight,
        horizontalArrangement,
        verticalArrangement,
        alignment
    ) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Horizontal,
            calculateCrossAxisCellConstraints = calculateRowCellHeightConstraints,
            fillCellSize = fillCellHeight,
            mainAxisArrangement = { totalSize, sizes, layoutDirection, density, outPosition ->
                with(horizontalArrangement) {
                    density.arrange(totalSize, sizes, layoutDirection, outPosition)
                }
            },
            mainAxisSpacing = horizontalArrangement.spacing,
            crossAxisArrangement = { totalSize, sizes, _, density, outPosition ->
                with(verticalArrangement) {
                    density.arrange(totalSize, sizes, outPosition)
                }
            },
            crossAxisSpacing = verticalArrangement.spacing,
            defaultAlignment = alignment
        )
    }
}

@PublishedApi
@Composable
internal fun rememberVerticalGridMeasurePolicy(
    calculateColumnCellWidthConstraints: Density.(Constraints) -> List<Int>,
    fillCellWidth: Boolean,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    alignment: Alignment
): MeasurePolicy {
    return remember(
        calculateColumnCellWidthConstraints,
        fillCellWidth,
        horizontalArrangement,
        verticalArrangement,
        alignment
    ) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Vertical,
            calculateCrossAxisCellConstraints = calculateColumnCellWidthConstraints,
            fillCellSize = fillCellWidth,
            mainAxisArrangement = { totalSize, sizes, _, density, outPosition ->
                with(verticalArrangement) {
                    density.arrange(totalSize, sizes, outPosition)
                }
            },
            mainAxisSpacing = verticalArrangement.spacing,
            crossAxisArrangement = { totalSize, sizes, layoutDirection, density, outPosition ->
                with(horizontalArrangement) {
                    density.arrange(totalSize, sizes, layoutDirection, outPosition)
                }
            },
            crossAxisSpacing = horizontalArrangement.spacing,
            defaultAlignment = alignment
        )
    }
}
