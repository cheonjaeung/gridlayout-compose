package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Constraints
import kotlin.jvm.JvmInline

private const val MaxSize = 0xFFFF

private const val MainAxisMinSizeBitOffset = 48
private const val MainAxisMaxSizeBitOffset = 32
private const val CrossAxisMinSizeBitOffset = 16

private const val UnpackMask = 0xFFFF

/**
 * Creates a [OrientationIndependentConstraints] to pack given sizes.
 */
@Stable
internal fun OrientationIndependentConstraints(
    mainAxisMinSize: Int,
    mainAxisMaxSize: Int,
    crossAxisMinSize: Int,
    crossAxisMaxSize: Int
): OrientationIndependentConstraints {
    return packOrientationIndependentConstraints(
        mainAxisMinSize,
        mainAxisMaxSize,
        crossAxisMinSize,
        crossAxisMaxSize
    )
}

/**
 * Creates a [OrientationIndependentConstraints] from given [Constraints] and [LayoutOrientation].
 */
@Stable
internal fun OrientationIndependentConstraints(
    o: LayoutOrientation,
    c: Constraints
): OrientationIndependentConstraints {
    return packOrientationIndependentConstraints(
        mainAxisMinSize = if (o == LayoutOrientation.Horizontal) c.minWidth else c.minHeight,
        mainAxisMaxSize = if (o == LayoutOrientation.Horizontal) c.maxWidth else c.maxHeight,
        crossAxisMinSize = if (o == LayoutOrientation.Horizontal) c.minHeight else c.minWidth,
        crossAxisMaxSize = if (o == LayoutOrientation.Horizontal) c.maxHeight else c.maxWidth,
    )
}

private fun packOrientationIndependentConstraints(
    mainAxisMinSize: Int,
    mainAxisMaxSize: Int,
    crossAxisMinSize: Int,
    crossAxisMaxSize: Int
): OrientationIndependentConstraints {
    require(mainAxisMinSize >= 0 && crossAxisMinSize >= 0) {
        "size must be positive"
    }

    require(mainAxisMinSize <= mainAxisMaxSize && crossAxisMinSize <= crossAxisMaxSize) {
        "minSize must be less than or equal to maxSize"
    }

    require(mainAxisMaxSize <= MaxSize && crossAxisMaxSize <= MaxSize) {
        "size must be less than $MaxSize"
    }

    // Pack 4 integer sizes into 64 bits.
    // | <----------------------------- 64 bits -------------------------------> |
    // | mainAxisMinSize | mainAxisMaxSize | crossAxisMinSize | crossAxisMaxSize |
    // | <---16 bits---> | <---16 bits---> | <----16 bits---> | <----16 bits---> |
    val packed = (mainAxisMinSize.toLong() shl MainAxisMinSizeBitOffset) or
        (mainAxisMaxSize.toLong() shl MainAxisMaxSizeBitOffset) or
        (crossAxisMinSize.toLong() shl CrossAxisMinSizeBitOffset) or
        crossAxisMaxSize.toLong()

    return OrientationIndependentConstraints(packed)
}

/**
 * Immutable constraints for measuring sequential grid layout, used in measure grid and items.
 *
 * This value class provides a unified way to represent layout constraints that are independent
 * of the current [LayoutOrientation]. It abstracts the layout direction is horizontal or vertical,
 * allowing the measurement logic to operate consistently on a "main axis" and a "cross axis".
 *
 * The [mainAxisMinSize] and [mainAxisMaxSize] refer to the constraints along the primary
 * direction of the layout (e.g., width if horizontal, height if vertical).
 * The [crossAxisMinSize] and [crossAxisMaxSize] refer to the constraints perpendicular
 * to the primary direction (e.g., height if horizontal, width if vertical).
 *
 * [OrientationIndependentConstraints] is implemented as an inline class to avoid object allocation
 * in measurement. Because the measurement logic can be invoked tens of times in a second.
 * If the constraints instance is created frequently, it can cause GC pressure and laggy frame.
 * For inlining this constraints representation, this class uses a single [Long] to pack and
 * represent the four integer sizes: [mainAxisMinSize], [mainAxisMaxSize], [crossAxisMinSize], and
 * [crossAxisMaxSize]. Each size is allocated 16 bits, allowing a maximum value of 65535 (2^16 - 1)
 * for each individual constraint component.
 */
@Immutable
@JvmInline
internal value class OrientationIndependentConstraints(val value: Long) {
    /**
     * The minimum size of the main axis in pixels.
     */
    val mainAxisMinSize: Int
        get() = (value shr MainAxisMinSizeBitOffset).toInt() and UnpackMask

    /**
     * The maximum size of the main axis in pixels.
     */
    val mainAxisMaxSize: Int
        get() = (value shr MainAxisMaxSizeBitOffset).toInt() and UnpackMask

    /**
     * The minimum size of the cross axis in pixels.
     */
    val crossAxisMinSize: Int
        get() = (value shr CrossAxisMinSizeBitOffset).toInt() and UnpackMask

    /**
     * The maximum size of the cross axis in pixels.
     */
    val crossAxisMaxSize: Int
        get() = (value and UnpackMask.toLong()).toInt()

    /**
     * Convert this to original [Constraints] class based on the layout orientation.
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

    override fun toString(): String {
        return "OrientationIndependentConstraints(" +
            "mainAxisMinSize=$mainAxisMinSize, " +
            "mainAxisMaxSize=$mainAxisMaxSize, " +
            "crossAxisMinSize=$crossAxisMinSize, " +
            "crossAxisMaxSize=$crossAxisMaxSize" +
            ")"
    }
}
