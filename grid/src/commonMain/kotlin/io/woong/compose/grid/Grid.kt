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

/**
 * A layout composable that places its children in a grid.
 *
 * @param rowCount The count of row.
 * @param modifier The modifier to be applied to this layout.
 * @param horizontalArrangement The horizontal arrangement of the layout children.
 * @param verticalArrangement The vertical arrangement of the layout children.
 * @param content The children composables of this layout.
 */
@Composable
@ExperimentalGridLayoutApi
inline fun HorizontalGrid(
    rowCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    val measurePolicy = horizontalGridMeasurePolicy(
        rowCount,
        horizontalArrangement,
        verticalArrangement
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
@Composable
@ExperimentalGridLayoutApi
inline fun VerticalGrid(
    columnCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {
    val measurePolicy = verticalGridMeasurePolicy(
        columnCount,
        horizontalArrangement,
        verticalArrangement
    )
    Layout(
        content = content,
        measurePolicy = measurePolicy,
        modifier = modifier,
    )
}

@PublishedApi
@Composable
internal fun horizontalGridMeasurePolicy(
    rowCount: Int,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
): MeasurePolicy {
    return remember(rowCount, horizontalArrangement, verticalArrangement) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Horizontal,
            crossAxisCount = rowCount,
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
internal fun verticalGridMeasurePolicy(
    columnCount: Int,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
): MeasurePolicy {
    return remember(columnCount, horizontalArrangement, verticalArrangement) {
        gridMeasurePolicy(
            orientation = LayoutOrientation.Vertical,
            crossAxisCount = columnCount,
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
