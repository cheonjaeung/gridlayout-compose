package com.cheonjaeung.compose.grid

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class BoxGridCellPositionElement(
    val row: Int? = null,
    val column: Int? = null,
    val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<BoxGridCellPositionNode>() {
    override fun create(): BoxGridCellPositionNode {
        return BoxGridCellPositionNode(row, column)
    }

    override fun update(node: BoxGridCellPositionNode) {
        node.row = row
        node.column = column
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is BoxGridCellPositionElement) return false
        return this.row == other.row && this.column == other.column
    }

    override fun hashCode(): Int {
        var hash = row.hashCode()
        hash = 31 * hash + column.hashCode()
        return hash
    }
}

internal class BoxGridCellPositionNode(
    var row: Int?,
    var column: Int?
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? BoxGridParentData ?: BoxGridParentData()
        if (row != null) {
            p.row = row
        }
        if (column != null) {
            p.column = column
        }
        return p
    }
}

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

internal class BoxGridAlignmentElement(
    val alignment: Alignment
) : ModifierNodeElement<BoxGridAlignmentNode>() {
    override fun create(): BoxGridAlignmentNode {
        return BoxGridAlignmentNode(alignment)
    }

    override fun update(node: BoxGridAlignmentNode) {
        node.alignment = alignment
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "align"
        value = alignment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is BoxGridAlignmentElement) return false
        return this.alignment == other.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }
}

internal class BoxGridAlignmentNode(
    var alignment: Alignment
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? BoxGridParentData ?: BoxGridParentData()
        p.alignment = alignment
        return p
    }
}
