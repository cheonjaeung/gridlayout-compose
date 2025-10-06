package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class HorizontalVerticalGridSpanElement(
    val span: GridItemSpanScope.() -> Int
) : ModifierNodeElement<HorizontalVerticalGridSpanNode>() {
    override fun create(): HorizontalVerticalGridSpanNode {
        return HorizontalVerticalGridSpanNode(span)
    }

    override fun update(node: HorizontalVerticalGridSpanNode) {
        node.span = span
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "span"
        value = span
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is HorizontalVerticalGridSpanElement) return false
        return this.span == other.span
    }

    override fun hashCode(): Int {
        return span.hashCode()
    }
}

internal class HorizontalVerticalGridSpanNode(
    var span: GridItemSpanScope.() -> Int
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? HorizontalVerticalGridParentData ?: HorizontalVerticalGridParentData()
        if (p.span == HorizontalVerticalGridParentData.DefaultSpan) {
            p.span = span
        }
        return p
    }
}
