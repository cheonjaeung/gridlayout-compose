package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

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
