package io.woong.compose.grid

/**
 * Common utilities for grid layout tests.
 */
object TestUtils {
    fun computeMainAxisCount(itemCount: Int, crossAxisCount: Int): Int {
        return if (itemCount % crossAxisCount == 0) {
            itemCount / crossAxisCount
        } else {
            itemCount / crossAxisCount + 1
        }
    }
}
