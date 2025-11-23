package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment

/**
 * A data class to contain modifier options of item at the specified position.
 */
internal data class SequentialGridParentData(
    var span: (GridItemSpanScope.() -> Int)? = null,
    var alignment: Alignment = DefaultAlignment
) {
    companion object Companion {
        internal val DefaultAlignment: Alignment = Alignment.TopStart
    }
}
