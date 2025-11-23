package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class SequentialGridSpanElement(
    val span: (GridItemSpanScope.() -> Int)?,
    val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<SequentialGridSpanNode>() {
    override fun create(): SequentialGridSpanNode {
        return SequentialGridSpanNode(span)
    }

    override fun update(node: SequentialGridSpanNode) {
        node.span = span
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is SequentialGridSpanElement) return false
        return this.span == other.span
    }

    override fun hashCode(): Int {
        return span.hashCode()
    }
}

internal class SequentialGridSpanNode(
    var span: (GridItemSpanScope.() -> Int)?
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? SequentialGridParentData ?: SequentialGridParentData()
        if (p.span == null) {
            p.span = span
        }
        return p
    }
}
