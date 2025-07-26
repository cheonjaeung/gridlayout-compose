package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.util.packInts
import androidx.compose.ui.util.unpackInt1
import androidx.compose.ui.util.unpackInt2
import kotlin.jvm.JvmInline

/**
 * Create a [BoxGridItemSpan] from the given [row] and [column] span size parameter..
 */
fun BoxGridItemSpan(row: Int = 1, column: Int = 1): BoxGridItemSpan {
    require(row > 0) { "span must be bigger than zero but rowSpan is $row" }
    require(column > 0) { "span must be bigger than zero but columnSpan is $column" }
    return BoxGridItemSpan(packedValue = packInts(row, column))
}

/**
 * A value class which represents the spans of an item in a [BoxGrid].
 */
@Immutable
@JvmInline
value class BoxGridItemSpan internal constructor(private val packedValue: Long) {
    @Stable
    val rowSpan: Int
        get() = unpackInt1(packedValue)

    @Stable
    val columnSpan: Int
        get() = unpackInt2(packedValue)

    @Stable
    operator fun component1(): Int = rowSpan

    @Stable
    operator fun component2(): Int = columnSpan
}

/**
 * A scope to calculate spans of items in the box grid layout.
 */
@GridItemScopeMarker
sealed interface BoxGridItemSpanScope {
    /**
     * The maximum current row line span.
     */
    val maxCurrentRowSpan: Int

    /**
     * The maximum current column line span.
     */
    val maxCurrentColumnSpan: Int

    /**
     * The maximum row line span. It will be the number of the rows for box grid.
     */
    val maxRowSpan: Int

    /**
     * The maximum column line span. It will be the number of the columns for box grid.
     */
    val maxColumnSpan: Int
}

internal class BoxGridItemSpanScopeImpl(
    override var maxCurrentRowSpan: Int,
    override var maxCurrentColumnSpan: Int,
    override var maxRowSpan: Int,
    override var maxColumnSpan: Int
) : BoxGridItemSpanScope
