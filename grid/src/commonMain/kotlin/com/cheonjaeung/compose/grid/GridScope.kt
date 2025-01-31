package com.cheonjaeung.compose.grid

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
    @Deprecated(
        "Span modifier with an integer parameter is deprecated. " +
            "Please use another span modifier with lambda parameter.",
        ReplaceWith("this.span { span }")
    )
    fun Modifier.span(span: Int): Modifier

    /**
     * Sets the span of the cell. The default span size is 1.
     *
     * @param span A span calculation lambda. If the result of [span] lambda is null, it means
     * that this item uses default span size.
     */
    @Stable
    fun Modifier.span(span: ((GridItemSpanScope.() -> Int))? = null): Modifier

    /**
     * Aligns the item to specific [Alignment] within the cell.
     */
    @Stable
    fun Modifier.align(alignment: Alignment): Modifier
}

internal object GridScopeInstance : GridScope {
    override fun Modifier.span(span: Int): Modifier {
        return this.then(GridSpanElement { span })
    }

    override fun Modifier.span(span: (GridItemSpanScope.() -> Int)?): Modifier {
        return this.then(GridSpanElement(span ?: GridParentData.DefaultSpan))
    }

    override fun Modifier.align(alignment: Alignment): Modifier {
        return this.then(GridAlignmentElement(alignment))
    }
}
