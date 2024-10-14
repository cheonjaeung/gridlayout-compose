package com.cheonjaeung.compose.grid

/**
 * A scope to calculate spans of items in the grid layout.
 */
@GridItemScopeMarker
sealed interface GridItemSpanScope {
    /**
     * The maximum current cross axis line span.
     */
    val maxCurrentLineSpan: Int

    /**
     * The maximum cross axis line span. It will be the number of columns for vertical grid or
     * rows for horizontal grid.
     *
     * For example, if the [VerticalGrid] has 4 columns, it will be 4.
     */
    val maxLineSpan: Int
}

internal class GridItemSpanScopeImpl(
    override var maxCurrentLineSpan: Int,
    override var maxLineSpan: Int
) : GridItemSpanScope
