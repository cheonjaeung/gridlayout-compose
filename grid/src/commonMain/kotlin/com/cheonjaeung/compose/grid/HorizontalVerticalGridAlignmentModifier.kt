package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class HorizontalVerticalGridAlignmentElement(
    val alignment: Alignment
) : ModifierNodeElement<HorizontalVerticalGridAlignmentNode>() {
    override fun create(): HorizontalVerticalGridAlignmentNode {
        return HorizontalVerticalGridAlignmentNode(alignment)
    }

    override fun update(node: HorizontalVerticalGridAlignmentNode) {
        node.alignment = alignment
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "align"
        value = alignment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is HorizontalVerticalGridAlignmentElement) return false
        return this.alignment == other.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }
}

internal class HorizontalVerticalGridAlignmentNode(
    var alignment: Alignment
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? HorizontalVerticalGridParentData ?: HorizontalVerticalGridParentData()
        p.alignment = alignment
        return p
    }
}
