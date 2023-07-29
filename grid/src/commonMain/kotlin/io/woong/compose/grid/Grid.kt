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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
 * @param horizontalArrangement The horizontal arrangement of the layout children.
 * @param verticalArrangement The vertical arrangement of the layout children.
 * @param content The children composables of this layout.
 */
@Composable
inline fun HorizontalGrid(
    rows: SimpleGridCells,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    val calculateRowCellHeightsFunction = rememberRowCellHeightConstraints(
        rows = rows,
        verticalArrangement = verticalArrangement,
    )
    val measurePolicy = rememberHorizontalGridMeasurePolicy(
        calculateRowCellHeightConstraints = calculateRowCellHeightsFunction,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    )
    Layout(
        content = content,
        measurePolicy = measurePolicy,
        modifier = modifier,
    )
}

/**
 * A layout composable that places its children in a grid.
 *
 * @param rowCount The count of row.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the layout children.
 * @param verticalArrangement The vertical arrangement of the layout children.
 * @param content The children composables of this layout.
 */
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("Use HorizontalGrid with rows parameter.")
@Composable
inline fun HorizontalGrid(
    rowCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    HorizontalGrid(
        rows = SimpleGridCells.Fixed(rowCount),
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        content = content,
    )
}

/**
 * A layout composable that places its children in a grid.
 *
 * @param columns The class that determines the sizes and the number of grid cells.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the layout children.
 * @param verticalArrangement The vertical arrangement of the layout children.
 * @param content The children composables of this layout.
 */
@Composable
inline fun VerticalGrid(
    columns: SimpleGridCells,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    val calculateColumnCellWidthsFunction = rememberColumnCellWidthConstraints(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
    )
    val measurePolicy = rememberVerticalGridMeasurePolicy(
        calculateColumnCellWidthConstraints = calculateColumnCellWidthsFunction,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    )
    Layout(
        content = content,
        measurePolicy = measurePolicy,
        modifier = modifier,
    )
}

/**
 * A layout composable that places its children in a grid.
 *
 * @param columnCount The count of column.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the layout children.
 * @param verticalArrangement The vertical arrangement of the layout children.
 * @param content The children composables of this layout.
 */
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("Use VerticalGrid with columns parameter.")
@Composable
inline fun VerticalGrid(
    columnCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    VerticalGrid(
        columns = SimpleGridCells.Fixed(columnCount),
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        content = content,
    )
}

@PublishedApi
@Composable
internal fun rememberRowCellHeightConstraints(
    rows: SimpleGridCells,
    verticalArrangement: Arrangement.Vertical,
): Density.(Constraints) -> List<GridCellConstraints> {
    return remember(rows, verticalArrangement) {
        { constraints ->
            val gridHeight = constraints.maxHeight
            if (gridHeight == Constraints.Infinity) {
                throw IllegalArgumentException(
                    "HorizontalGrid's height should be measurable, not an infinite."
                )
            }
            with(rows) {
                val minConstraints = calculateCrossAxisMinCellSizes(
                    availableSize = gridHeight,
                    spacing = verticalArrangement.spacing.roundToPx(),
                )
                val maxConstraints = calculateCrossAxisMaxCellSizes(
                    availableSize = gridHeight,
                    spacing = verticalArrangement.spacing.roundToPx(),
                )
                minConstraints.zip(maxConstraints) { min, max ->
                    GridCellConstraints(min, max)
                }
            }
        }
    }
}

@PublishedApi
@Composable
internal fun rememberColumnCellWidthConstraints(
    columns: SimpleGridCells,
    horizontalArrangement: Arrangement.Horizontal,
): Density.(Constraints) -> List<GridCellConstraints> {
    return remember(columns, horizontalArrangement) {
        { constraints ->
            val gridWidth = constraints.maxWidth
            if (gridWidth == Constraints.Infinity) {
                throw IllegalArgumentException(
                    "VerticalGrid's width should be measurable, not an infinite."
                )
            }
            with(columns) {
                val minConstraints = calculateCrossAxisMinCellSizes(
                    availableSize = gridWidth,
                    spacing = horizontalArrangement.spacing.roundToPx(),
                )
                val maxConstraints = calculateCrossAxisMaxCellSizes(
                    availableSize = gridWidth,
                    spacing = horizontalArrangement.spacing.roundToPx(),
                )
                minConstraints.zip(maxConstraints) { min, max ->
                    GridCellConstraints(min, max)
                }
            }
        }
    }
}

@PublishedApi
@Composable
internal fun rememberHorizontalGridMeasurePolicy(
    calculateRowCellHeightConstraints: Density.(Constraints) -> List<GridCellConstraints>,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
): MeasurePolicy {
    return remember(calculateRowCellHeightConstraints, horizontalArrangement, verticalArrangement) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Horizontal,
            calculateCrossAxisCellConstraints = calculateRowCellHeightConstraints,
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
        )
    }
}

@PublishedApi
@Composable
internal fun rememberVerticalGridMeasurePolicy(
    calculateColumnCellWidthConstraints: Density.(Constraints) -> List<GridCellConstraints>,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
): MeasurePolicy {
    return remember(calculateColumnCellWidthConstraints, horizontalArrangement, verticalArrangement) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Vertical,
            calculateCrossAxisCellConstraints = calculateColumnCellWidthConstraints,
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
        )
    }
}
