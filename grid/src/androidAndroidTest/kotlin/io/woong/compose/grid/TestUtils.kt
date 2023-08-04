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

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

/**
 * Asserts that the layout of this nod has size equal to [expectedSize].
 */
fun SemanticsNodeInteraction.assertSizeIsEqualTo(
    expectedSize: Dp,
): SemanticsNodeInteraction {
    return this
        .assertWidthIsEqualTo(expectedSize)
        .assertHeightIsEqualTo(expectedSize)
}

/**
 * Asserts that the layout of this nod has size equal to [expectedWidth] and [expectedHeight].
 */
fun SemanticsNodeInteraction.assertSizeIsEqualTo(
    expectedWidth: Dp,
    expectedHeight: Dp
): SemanticsNodeInteraction {
    return this
        .assertWidthIsEqualTo(expectedWidth)
        .assertHeightIsEqualTo(expectedHeight)
}

/**
 * Calculates expected main axis count by cross axis count and item count.
 */
fun calculateMainAxisCount(itemCount: Int, crossAxisCount: Int): Int {
    return if (itemCount % crossAxisCount == 0) {
        itemCount / crossAxisCount
    } else {
        itemCount / crossAxisCount + 1
    }
}

/**
 * Returns expected cross axis count for grid layout using [SimpleGridCells.Adaptive].
 */
fun expectAdaptiveGridCrossAxisCount(gridSize: Dp, minSize: Dp): Int {
    return gridSize.value.roundToInt() / minSize.value.roundToInt()
}
