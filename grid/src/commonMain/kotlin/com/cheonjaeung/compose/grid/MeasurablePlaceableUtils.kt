package com.cheonjaeung.compose.grid

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.IntSize

/**
 * Calculates the max intrinsic size of the [Measurable] in the given [orientation].
 */
internal fun Measurable.maxIntrinsicSize(
    orientation: LayoutOrientation,
    crossAxisSize: Int
): Int {
    return when (orientation) {
        LayoutOrientation.Horizontal -> maxIntrinsicWidth(crossAxisSize)
        LayoutOrientation.Vertical -> maxIntrinsicHeight(crossAxisSize)
    }
}

/**
 * Returns an [IntSize] containing the width and height size of the [Placeable].
 */
internal fun Placeable.size(): IntSize {
    return IntSize(width, height)
}

/**
 * Returns the size of the main axis of the [Placeable].
 */
internal fun Placeable.mainAxisSize(orientation: LayoutOrientation): Int {
    return if (orientation == LayoutOrientation.Horizontal) {
        width
    } else {
        height
    }
}
