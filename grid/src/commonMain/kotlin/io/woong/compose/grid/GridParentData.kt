package io.woong.compose.grid

import androidx.compose.ui.Alignment

/**
 * A data class to contain modifier options of item at the specified position.
 */
internal data class GridParentData(
    var span: Int = DefaultSpan,
    var alignment: Alignment = DefaultAlignment
) {
    companion object {
        internal const val DefaultSpan: Int = 1
        internal val DefaultAlignment: Alignment = Alignment.TopStart
    }
}
