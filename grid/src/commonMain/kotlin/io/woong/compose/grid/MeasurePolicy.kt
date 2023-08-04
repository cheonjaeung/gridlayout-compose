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
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

internal fun gridMeasurePolicy(
    orientation: LayoutOrientation,
    calculateCrossAxisCellConstraints: Density.(Constraints) -> List<Int>,
    mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    mainAxisSpacing: Dp,
    crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    crossAxisSpacing: Dp,
): MeasurePolicy {
    @Suppress("ObjectLiteralToLambda")
    return object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            val placeables: Array<Placeable?> = arrayOfNulls(measurables.size)
            val crossAxisCellConstraintsList = calculateCrossAxisCellConstraints(constraints)
            val crossAxisCellCount = crossAxisCellConstraintsList.size

            val measureHelper = GridMeasureHelper(
                orientation = orientation,
                measurables = measurables,
                placeables = placeables,
                crossAxisCellConstraintsList = crossAxisCellConstraintsList,
                crossAxisCount = crossAxisCellCount,
                mainAxisArrangement = mainAxisArrangement,
                mainAxisSpacing = mainAxisSpacing,
                crossAxisArrangement = crossAxisArrangement,
                crossAxisSpacing = crossAxisSpacing,
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
                    measureResult = measureResult,
                    arrangeResult = arrangeResult,
                )
            }
        }
    }
}
