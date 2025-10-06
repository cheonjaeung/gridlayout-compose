package com.cheonjaeung.compose.grid

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.debugInspectorInfo

/**
 * A Scope for the children of [HorizontalGrid] or [VerticalGrid].
 */
@Immutable
@LayoutScopeMarker
interface GridScope {
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
    override fun Modifier.span(span: (GridItemSpanScope.() -> Int)?): Modifier {
        return this.then(
            HorizontalVerticalGridSpanElement(
                span = span,
                inspectorInfo = debugInspectorInfo {
                    name = "span"
                    value = span
                }
            )
        )
    }

    override fun Modifier.align(alignment: Alignment): Modifier {
        return this.then(HorizontalVerticalGridAlignmentElement(alignment))
    }
}
