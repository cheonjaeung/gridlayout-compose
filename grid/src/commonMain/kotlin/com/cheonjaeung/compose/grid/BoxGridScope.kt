package com.cheonjaeung.compose.grid

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.debugInspectorInfo

/**
 * A scope for the children of [BoxGrid].
 */
@Immutable
@LayoutScopeMarker
@ExperimentalGridApi
interface BoxGridScope {
    /**
     * Sets the row position of the cell. The row position starts from 0 and
     * the default is 0.
     */
    @Stable
    @ExperimentalGridApi
    fun Modifier.row(row: Int): Modifier

    /**
     * Sets the column position of the cell. The column position starts from 0 and
     * the default is 0.
     */
    @Stable
    @ExperimentalGridApi
    fun Modifier.column(column: Int): Modifier

    /**
     * Sets the row and column span of the cell. The default span size is 1.
     *
     * @param span A span calculation lambda. If the result is null, it means that this item uses
     * the default span size.
     */
    @Stable
    @ExperimentalGridApi
    fun Modifier.span(span: ((BoxGridItemSpanScope).() -> BoxGridItemSpan)? = null): Modifier

    /**
     * Aligns the item to specific [Alignment] within the cell.
     */
    @Stable
    @ExperimentalGridApi
    fun Modifier.align(alignment: Alignment): Modifier
}

@OptIn(ExperimentalGridApi::class)
internal object BoxGridScopeInstance : BoxGridScope {
    override fun Modifier.row(row: Int): Modifier {
        require(row >= 0) { "$row is invalid value, must be zero or positive" }
        return this.then(
            BoxGridRowColumnElement(
                row = row,
                inspectorInfo = debugInspectorInfo {
                    name = "row"
                    value = row
                }
            )
        )
    }

    override fun Modifier.column(column: Int): Modifier {
        require(column >= 0) { "$column is invalid value, must be zero or positive" }
        return this.then(
            BoxGridRowColumnElement(
                column = column,
                inspectorInfo = debugInspectorInfo {
                    name = "column"
                    value = column
                }
            )
        )
    }

    override fun Modifier.span(span: (BoxGridItemSpanScope.() -> BoxGridItemSpan)?): Modifier {
        return this.then(
            BoxGridSpanElement(
                span = span,
                inspectorInfo = debugInspectorInfo {
                    name = "span"
                    value = span
                }
            )
        )
    }

    override fun Modifier.align(alignment: Alignment): Modifier {
        return this.then(BoxGridAlignmentElement(alignment))
    }
}
