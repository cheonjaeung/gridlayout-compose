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

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A Scope for the children of [HorizontalGrid] or [VerticalGrid].
 */
@Immutable
@LayoutScopeMarker
interface GridScope {
    /**
     * Sets the span of the cell. The default span size is 1.
     *
     * If the given span is bigger than current line's remaining span, this cell will be placed
     * at the next line.
     */
    @Stable
    fun Modifier.span(span: Int): Modifier

    /**
     * Aligns the item to specific [Alignment] within the cell.
     */
    @Stable
    fun Modifier.align(alignment: Alignment): Modifier
}

internal object GridScopeInstance : GridScope {
    override fun Modifier.span(span: Int): Modifier {
        require(span > 0) { "span must be bigger than zero, $span is zero or negative" }
        return this.then(GridSpanElement(span))
    }

    override fun Modifier.align(alignment: Alignment): Modifier {
        return this.then(GridAlignmentElement(alignment))
    }
}
