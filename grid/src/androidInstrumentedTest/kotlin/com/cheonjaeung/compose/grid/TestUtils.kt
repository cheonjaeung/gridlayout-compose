package com.cheonjaeung.compose.grid

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.unit.width
import kotlin.math.roundToInt

/**
 * Asserts that the layout of this nod has size equal to [expectedSize].
 *
 * This function is from compose test library to customize tolerance.
 */
fun SemanticsNodeInteraction.assertSizeIsEqualTo(
    expectedSize: Dp,
    tolerance: Dp = 1.dp
): SemanticsNodeInteraction {
    return this
        .assertWidthIsEqualTo(expectedSize, tolerance)
        .assertHeightIsEqualTo(expectedSize, tolerance)
}

/**
 * Asserts that the layout of this nod has size equal to [expectedWidth] and [expectedHeight].
 *
 * This function is from compose test library to customize tolerance.
 */
fun SemanticsNodeInteraction.assertSizeIsEqualTo(
    expectedWidth: Dp,
    expectedHeight: Dp,
    tolerance: Dp = 1.dp
): SemanticsNodeInteraction {
    return this
        .assertWidthIsEqualTo(expectedWidth, tolerance)
        .assertHeightIsEqualTo(expectedHeight, tolerance)
}

/**
 * Asserts that the layout of this node has width equal to [expectedWidth].
 *
 * This function is from compose test library to customize tolerance.
 */
fun SemanticsNodeInteraction.assertWidthIsEqualTo(
    expectedWidth: Dp,
    tolerance: Dp = 1.dp
): SemanticsNodeInteraction {
    return withUnclippedBoundsInRoot {
        it.width.assertIsEqualTo(expectedWidth, "width", tolerance)
    }
}

/**
 * Asserts that the layout of this node has height equal to [expectedHeight].
 *
 * This function is from compose test library to customize tolerance.
 */
fun SemanticsNodeInteraction.assertHeightIsEqualTo(
    expectedHeight: Dp,
    tolerance: Dp = 1.dp
): SemanticsNodeInteraction {
    return withUnclippedBoundsInRoot {
        it.height.assertIsEqualTo(expectedHeight, "width", tolerance)
    }
}

/**
 * Asserts that the layout of this node has position in the root composable that is
 * equal to the given position.
 *
 * This function is from compose test library to customize tolerance.
 */
fun SemanticsNodeInteraction.assertPositionInRootIsEqualTo(
    expectedLeft: Dp,
    expectedTop: Dp,
    tolerance: Dp = 1.dp
): SemanticsNodeInteraction {
    return withUnclippedBoundsInRoot {
        it.left.assertIsEqualTo(expectedLeft, "left", tolerance)
        it.top.assertIsEqualTo(expectedTop, "top", tolerance)
    }
}

private fun SemanticsNodeInteraction.withUnclippedBoundsInRoot(
    assertion: (DpRect) -> Unit
): SemanticsNodeInteraction {
    val node = fetchSemanticsNode("Failed to retrieve bounds of the node.")
    val bounds = with(node.layoutInfo.density) {
        node.unclippedBoundsInRoot.let {
            DpRect(it.left.toDp(), it.top.toDp(), it.right.toDp(), it.bottom.toDp())
        }
    }
    assertion.invoke(bounds)
    return this
}

private val SemanticsNode.unclippedBoundsInRoot: Rect
    get() {
        return if (layoutInfo.isPlaced) {
            Rect(positionInRoot, size.toSize())
        } else {
            Dp.Unspecified.value.let { Rect(it, it, it, it) }
        }
    }

/**
 * Returns expected cross axis count for grid layout using [SimpleGridCells.Adaptive].
 */
fun expectAdaptiveGridCrossAxisCount(gridSize: Dp, minSize: Dp): Int {
    return gridSize.value.roundToInt() / minSize.value.roundToInt()
}
