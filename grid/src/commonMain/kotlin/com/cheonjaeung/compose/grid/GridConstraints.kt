package com.cheonjaeung.compose.grid

import androidx.compose.ui.unit.Constraints

/**
 * A constraints class for measuring grid layout.
 *
 * When orientation is horizontal, the x is the main axis and y is cross axis. In other word,
 * when vertical, the y is the main axis and x is cross axis.
 */
internal data class OrientationIndependentConstraints(
    val mainAxisMinSize: Int,
    val mainAxisMaxSize: Int,
    val crossAxisMinSize: Int,
    val crossAxisMaxSize: Int,
) {
    constructor(o: LayoutOrientation, c: Constraints) : this(
        mainAxisMinSize = if (o == LayoutOrientation.Horizontal) c.minWidth else c.minHeight,
        mainAxisMaxSize = if (o == LayoutOrientation.Horizontal) c.maxWidth else c.maxHeight,
        crossAxisMinSize = if (o == LayoutOrientation.Horizontal) c.minHeight else c.minWidth,
        crossAxisMaxSize = if (o == LayoutOrientation.Horizontal) c.maxHeight else c.maxWidth,
    )

    /**
     * Convert this to original [Constraints] class.
     */
    fun toConstraints(orientation: LayoutOrientation): Constraints {
        return if (orientation == LayoutOrientation.Horizontal) {
            Constraints(
                minWidth = mainAxisMinSize,
                maxWidth = mainAxisMaxSize,
                minHeight = crossAxisMinSize,
                maxHeight = crossAxisMaxSize
            )
        } else {
            Constraints(
                minWidth = crossAxisMinSize,
                maxWidth = crossAxisMaxSize,
                minHeight = mainAxisMinSize,
                maxHeight = mainAxisMaxSize
            )
        }
    }
}
