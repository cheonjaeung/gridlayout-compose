package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class BoxGridSpanElement(
    val span: (BoxGridItemSpanScope.() -> BoxGridItemSpan)? = null,
    val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<BoxGridSpanNode>() {
    override fun create(): BoxGridSpanNode {
        return BoxGridSpanNode(span)
    }

    override fun update(node: BoxGridSpanNode) {
        node.span = span
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is BoxGridSpanElement) return false
        return this.span == other.span
    }

    override fun hashCode(): Int {
        return span.hashCode()
    }
}

internal class BoxGridSpanNode(
    var span: (BoxGridItemSpanScope.() -> BoxGridItemSpan)?
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? BoxGridParentData ?: BoxGridParentData()
        if (p.span == null) {
            p.span = span
        }
        return p
    }
}
