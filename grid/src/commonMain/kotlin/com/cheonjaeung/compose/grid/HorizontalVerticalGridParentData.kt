package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment

/**
 * A data class to contain modifier options of item at the specified position.
 */
internal data class HorizontalVerticalGridParentData(
    var span: (GridItemSpanScope.() -> Int)? = null,
    var alignment: Alignment? = null
)
