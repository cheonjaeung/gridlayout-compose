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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalGridLayoutApi::class)
@RunWith(AndroidJUnit4::class)
class GridArrangementTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testHorizontalGrid_withHorizontalArrangementStart_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier.testTag("grid"),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementStart_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier.testTag("grid"),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementStart_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementStart_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementEnd_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.End,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (columnCount - column)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementEnd_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.End,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (columnCount - column)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementEnd_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.End,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * (columnCount - (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementEnd_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.End,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * (columnCount - (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementCenter_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val centerLeft = (gridWidth - (childSize * columnCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = centerLeft + (childSize * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementCenter_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val centerLeft = (gridWidth - (childSize * columnCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = centerLeft + (childSize * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementCenter_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val centerLeft = (gridWidth - (childSize * columnCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = centerLeft + (childSize * (columnCount - (column + 1))),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementCenter_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val centerLeft = (gridWidth - (childSize * columnCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = centerLeft + (childSize * (columnCount - (column + 1))),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementTop() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Top,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementTop() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Top,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementBottom() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Bottom,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = gridHeight - (childSize * (rowCount - row))
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementBottom() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Bottom,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = gridHeight - (childSize * (rowCount - row))
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementCenter() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Center,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val centerTop = (gridHeight - (childSize * rowCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = centerTop + (childSize * row)
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementCenter() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.Center,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val centerTop = (gridHeight - (childSize * rowCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = centerTop + (childSize * row)
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceEvenly_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedSpacing * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceEvenly_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedSpacing * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceEvenly_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedSpacing * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceEvenly_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedSpacing * (column + 1)),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementSpaceEvenly() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridHeight - (childSize * rowCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedSpacing * (row + 1))
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementSpaceEvenly() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val spaceCount = columnCount + 1
        val expectedSpacing = (gridHeight - (childSize * rowCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedSpacing * (row + 1))
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceBetween_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedSpacing * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceBetween_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedSpacing * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceBetween_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedSpacing * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceBetween_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridWidth - (childSize * columnCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedSpacing * column),
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementSpaceBetween() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridHeight - (childSize * rowCount)) / spaceCount
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedSpacing * row)
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementSpaceBetween() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val spaceCount = columnCount - 1
        val expectedSpacing = (gridHeight - (childSize * rowCount)) / spaceCount
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedSpacing * row)
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceAround_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridWidth - (childSize * rowCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedMiddleSpacing * column) + expectedEndSpacing,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceAround_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridWidth - (childSize * rowCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (childSize * column) + (expectedMiddleSpacing * column) + expectedEndSpacing,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementSpaceAround_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rowCount = rowCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridWidth - (childSize * rowCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedMiddleSpacing * column) - expectedEndSpacing,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementSpaceAround_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columnCount = columnCount,
                    modifier = Modifier
                        .width(gridWidth)
                        .testTag("grid"),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    for (tag in testTags) {
                        Box(
                            modifier = Modifier
                                .size(childSize)
                                .testTag(tag)
                        )
                    }
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridWidth - (childSize * rowCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = gridWidth - (childSize * (column + 1)) - (expectedMiddleSpacing * column) - expectedEndSpacing,
                            expectedTop = childSize * row
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementSpaceAround() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridHeight - (childSize * columnCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedMiddleSpacing * row) + expectedEndSpacing
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementSpaceAround() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid"),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        val endSpacingCount = 2
        val middleSpacingCount = columnCount - 1
        val expectedEndSpacing = (gridHeight - (childSize * columnCount)) / (endSpacingCount + (middleSpacingCount * 2))
        val expectedMiddleSpacing = expectedEndSpacing * 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column,
                            expectedTop = (childSize * row) + (expectedMiddleSpacing * row) + expectedEndSpacing
                        )
                    i++
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementSpaceBy() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val childSize = 20.dp
        val horizontalSpacing = 5.dp
        val verticalSpacing = 10.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier.testTag("grid"),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column + (horizontalSpacing * column),
                            expectedTop = childSize * row + (verticalSpacing * row)
                        )
                    i++
                }
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo((childSize * columnCount) + (horizontalSpacing * (columnCount - 1)))
            .assertHeightIsEqualTo((childSize * rowCount) + (verticalSpacing * (rowCount - 1)))
    }

    @Test
    fun testVerticalGrid_withArrangementSpaceBy() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
        val childSize = 20.dp
        val horizontalSpacing = 5.dp
        val verticalSpacing = 10.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier.testTag("grid"),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                for (tag in testTags) {
                    Box(
                        modifier = Modifier
                            .size(childSize)
                            .testTag(tag)
                    )
                }
            }
        }

        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    composeRule
                        .onNode(hasTestTag(testTags[i]))
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = childSize * column + (horizontalSpacing * column),
                            expectedTop = childSize * row + (verticalSpacing * row)
                        )
                    i++
                }
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo((childSize * columnCount) + (horizontalSpacing * (columnCount - 1)))
            .assertHeightIsEqualTo((childSize * rowCount) + (verticalSpacing * (rowCount - 1)))
    }
}
