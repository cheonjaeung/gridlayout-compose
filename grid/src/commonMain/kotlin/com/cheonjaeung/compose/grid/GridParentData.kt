package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment

/**
 * A data class to contain modifier options of item at the specified position.
 */
internal data class GridParentData(
    var span: GridItemSpanScope.(index: Int) -> Int = DefaultSpan,
    var alignment: Alignment = DefaultAlignment
) {
    companion object {
        internal val DefaultSpan: GridItemSpanScope.(index: Int) -> Int = { 1 }
        internal val DefaultAlignment: Alignment = Alignment.TopStart
    }
}
