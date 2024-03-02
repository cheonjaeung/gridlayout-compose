package io.woong.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.unit.Density

internal class GridSpanElement(val span: Int) : ModifierNodeElement<GridSpanNode>() {
    override fun create(): GridSpanNode {
        return GridSpanNode(span)
    }

    override fun update(node: GridSpanNode) {
        node.span = span
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is GridSpanElement) return false
        return this.span == other.span
    }

    override fun hashCode(): Int {
        return span.hashCode()
    }
}

internal class GridSpanNode(var span: Int) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? GridParentData ?: GridParentData()
        p.span = span
        return p
    }
}
