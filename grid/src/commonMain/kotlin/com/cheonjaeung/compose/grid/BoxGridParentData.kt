package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment

internal data class BoxGridParentData(
    var row: Int = UNSPECIFIED_ROW,
    var column: Int = UNSPECIFIED_COLUMN,
    var span: (BoxGridItemSpanScope.() -> BoxGridItemSpan)? = null,
    var alignment: Alignment? = null
) {
    companion object {
        internal const val UNSPECIFIED_ROW: Int = -1
        internal const val UNSPECIFIED_COLUMN: Int = -1
        internal const val DEFAULT_ROW: Int = 0
        internal const val DEFAULT_COLUMN: Int = 0
    }
}
