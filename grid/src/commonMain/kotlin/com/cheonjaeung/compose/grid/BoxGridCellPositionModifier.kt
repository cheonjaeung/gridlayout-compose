package com.cheonjaeung.compose.grid

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Density

internal class BoxGridCellPositionElement(
    val row: Int = BoxGridParentData.UNSPECIFIED_ROW,
    val column: Int = BoxGridParentData.UNSPECIFIED_COLUMN,
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
    var row: Int,
    var column: Int
) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? BoxGridParentData ?: BoxGridParentData()
        if (row != BoxGridParentData.UNSPECIFIED_ROW) {
            p.row = row
        }
        if (column != BoxGridParentData.UNSPECIFIED_COLUMN) {
            p.column = column
        }
        return p
    }
}
