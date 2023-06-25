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
