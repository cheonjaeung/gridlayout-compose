package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment

internal data class BoxGridParentData(
    var row: Int? = null,
    var column: Int? = null,
    var span: (BoxGridItemSpanScope.() -> BoxGridItemSpan)? = null,
    var alignment: Alignment? = null
)
