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
