/*
 * Copyright 2023 Jaewoong Cheon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.woong.compose.grid

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.unit.Density

internal class GridAlignmentElement(val alignment: Alignment) : ModifierNodeElement<GridAlignmentNode>() {
    override fun create(): GridAlignmentNode {
        return GridAlignmentNode(alignment)
    }

    override fun update(node: GridAlignmentNode) {
        node.alignment = alignment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is GridAlignmentElement) return false
        return this.alignment == other.alignment
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }
}

internal class GridAlignmentNode(var alignment: Alignment) : Modifier.Node(), ParentDataModifierNode {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val p = parentData as? GridParentData ?: GridParentData()
        p.alignment = alignment
        return p
    }
}
