package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Constraints
import kotlin.jvm.JvmInline

private const val Infinity = 0xFFFF

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
    val mainAxisMaxSizeValue = if (mainAxisMaxSize == Constraints.Infinity) Infinity else mainAxisMaxSize
    val crossAxisMaxSizeValue = if (crossAxisMaxSize == Constraints.Infinity) Infinity else crossAxisMaxSize

    require(mainAxisMinSize >= 0 && crossAxisMinSize >= 0) {
        "size must be positive: " +
            "mainAxisMinSize=$mainAxisMinSize, crossAxisMinSize=$crossAxisMinSize"
    }

    require(mainAxisMinSize <= mainAxisMaxSizeValue && crossAxisMinSize <= crossAxisMaxSizeValue) {
        "minSize must be less than or equal to maxSize: " +
            "mainAxisSize=(min=$mainAxisMinSize, max=$mainAxisMaxSize), " +
            "crossAxisSize=(min=$crossAxisMinSize, max=$crossAxisMaxSize)"
    }

    require(
        (mainAxisMaxSize == Constraints.Infinity || mainAxisMaxSize < Infinity) &&
            (crossAxisMaxSize == Constraints.Infinity || crossAxisMaxSize < Infinity)
    ) {
        "size must be less than $Infinity or equal to Infinity: " +
            "mainAxisMaxSize=$mainAxisMaxSize, crossAxisMaxSize=$crossAxisMaxSize"
    }

    // Pack 4 integer sizes into 64 bits.
    // | <----------------------------- 64 bits -------------------------------> |
    // | mainAxisMinSize | mainAxisMaxSize | crossAxisMinSize | crossAxisMaxSize |
    // | <---16 bits---> | <---16 bits---> | <----16 bits---> | <----16 bits---> |
    val packed = (mainAxisMinSize.toLong() shl MainAxisMinSizeBitOffset) or
        (mainAxisMaxSizeValue.toLong() shl MainAxisMaxSizeBitOffset) or
        (crossAxisMinSize.toLong() shl CrossAxisMinSizeBitOffset) or
        crossAxisMaxSizeValue.toLong()

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
 * [crossAxisMaxSize]. Each size is allocated 16 bits, allowing a maximum value of 65534
 * for each individual constraint component. 65535 is used to represent infinity.
 */
@Immutable
@JvmInline
internal value class OrientationIndependentConstraints(val value: Long) {
    /**
     * The minimum size of the main axis in pixels.
     */
    val mainAxisMinSize: Int
        get() = unpackedSizeOrInfinity((value shr MainAxisMinSizeBitOffset).toInt() and UnpackMask)

    /**
     * The maximum size of the main axis in pixels.
     */
    val mainAxisMaxSize: Int
        get() = unpackedSizeOrInfinity((value shr MainAxisMaxSizeBitOffset).toInt() and UnpackMask)

    /**
     * The minimum size of the cross axis in pixels.
     */
    val crossAxisMinSize: Int
        get() = unpackedSizeOrInfinity((value shr CrossAxisMinSizeBitOffset).toInt() and UnpackMask)

    /**
     * The maximum size of the cross axis in pixels.
     */
    val crossAxisMaxSize: Int
        get() = unpackedSizeOrInfinity((value and UnpackMask.toLong()).toInt())

    private fun unpackedSizeOrInfinity(value: Int): Int {
        return if (value == Infinity) Constraints.Infinity else value
    }

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
        val mainAxisMinStr = if (mainAxisMinSize == Constraints.Infinity) "Infinity" else "$mainAxisMinSize"
        val mainAxisMaxStr = if (mainAxisMaxSize == Constraints.Infinity) "Infinity" else "$mainAxisMaxSize"
        val crossAxisMinStr = if (crossAxisMinSize == Constraints.Infinity) "Infinity" else "$crossAxisMinSize"
        val crossAxisMaxStr = if (crossAxisMaxSize == Constraints.Infinity) "Infinity" else "$crossAxisMaxSize"

        return "OrientationIndependentConstraints(" +
            "mainAxisMinSize=$mainAxisMinStr, " +
            "mainAxisMaxSize=$mainAxisMaxStr, " +
            "crossAxisMinSize=$crossAxisMinStr, " +
            "crossAxisMaxSize=$crossAxisMaxStr" +
            ")"
    }
}
