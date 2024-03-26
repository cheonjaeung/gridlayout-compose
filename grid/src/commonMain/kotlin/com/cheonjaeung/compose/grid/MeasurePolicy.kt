package com.cheonjaeung.compose.grid

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

internal fun gridMeasurePolicy(
    orientation: LayoutOrientation,
    calculateCrossAxisCellConstraints: Density.(Constraints) -> List<Int>,
    fillCellSize: Boolean,
    mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    mainAxisSpacing: Dp,
    crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    crossAxisSpacing: Dp
): MeasurePolicy {
    return GridMeasurePolicy(
        orientation,
        calculateCrossAxisCellConstraints,
        fillCellSize,
        mainAxisArrangement,
        mainAxisSpacing,
        crossAxisArrangement,
        crossAxisSpacing
    )
}

private class GridMeasurePolicy(
    private val orientation: LayoutOrientation,
    private val calculateCrossAxisCellConstraints: Density.(Constraints) -> List<Int>,
    private val fillCellSize: Boolean,
    private val mainAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    private val mainAxisSpacing: Dp,
    private val crossAxisArrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit,
    private val crossAxisSpacing: Dp
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        val crossAxisCellConstraintsList = calculateCrossAxisCellConstraints(constraints)
        val crossAxisCellCount = crossAxisCellConstraintsList.size

        val measureHelper = GridMeasureHelper(
            orientation = orientation,
            measurables = measurables,
            crossAxisCellConstraintsList = crossAxisCellConstraintsList,
            fillCellSize = fillCellSize,
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
                arrangeResult = arrangeResult,
            )
        }
    }
}
