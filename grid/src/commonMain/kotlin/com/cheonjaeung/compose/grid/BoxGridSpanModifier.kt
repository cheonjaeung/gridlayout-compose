package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class BoxGridSpanElement(
    val rowSpan: (GridItemSpanScope.() -> Int)? = null,
    val columnSpan: (GridItemSpanScope.() -> Int)? = null,
    val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<BoxGridSpanNode>() {
    override fun create(): BoxGridSpanNode {
        return BoxGridSpanNode(rowSpan, columnSpan)
    }

    override fun update(node: BoxGridSpanNode) {
        node.rowSpan = rowSpan
        node.columnSpan = columnSpan
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is BoxGridSpanElement) return false
        return this.rowSpan == other.rowSpan && this.columnSpan == other.columnSpan
    }

    override fun hashCode(): Int {
        var hash = rowSpan.hashCode()
        hash = 31 * hash + columnSpan.hashCode()
        return hash
    }
}

internal class BoxGridSpanNode(
    var rowSpan: (GridItemSpanScope.() -> Int)?,
    var columnSpan: (GridItemSpanScope.() -> Int)?
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? BoxGridParentData ?: BoxGridParentData()
        rowSpan?.let {
            p.rowSpan = it
        }
        columnSpan?.let {
            p.columnSpan = it
        }
        return p
    }
}
