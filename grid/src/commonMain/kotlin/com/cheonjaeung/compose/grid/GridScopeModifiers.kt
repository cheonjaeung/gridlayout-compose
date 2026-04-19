package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment
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

internal class SequentialGridAlignmentElement(
    val alignment: Alignment
) : ModifierNodeElement<SequentialGridAlignmentNode>() {
    override fun create(): SequentialGridAlignmentNode {
        return SequentialGridAlignmentNode(alignment)
    }

    override fun update(node: SequentialGridAlignmentNode) {
        node.alignment = alignment
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "align"
        value = alignment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is SequentialGridAlignmentElement) return false
        return this.alignment == other.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }
}

internal class SequentialGridAlignmentNode(
    var alignment: Alignment
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? SequentialGridParentData ?: SequentialGridParentData()
        p.alignment = alignment
        return p
    }
}

internal class SequentialGridFillMainAxisSizeElement(
    val fraction: Float
) : ModifierNodeElement<SequentialGridFillMainAxisSizeNode>() {
    override fun create(): SequentialGridFillMainAxisSizeNode {
        return SequentialGridFillMainAxisSizeNode(fraction)
    }

    override fun update(node: SequentialGridFillMainAxisSizeNode) {
        node.fraction = fraction
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "fillMaxMainAxisSize"
        properties["fraction"] = fraction
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is SequentialGridFillMainAxisSizeElement) return false
        return fraction == other.fraction
    }

    override fun hashCode(): Int {
        return fraction.hashCode()
    }
}

internal class SequentialGridFillMainAxisSizeNode(
    var fraction: Float
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? SequentialGridParentData ?: SequentialGridParentData()
        if (p.mainAxisSizeFraction != fraction) {
            p.mainAxisSizeFraction = fraction
        }
        return p
    }
}
