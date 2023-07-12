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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
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
class GridTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testHorizontalGrid_noChild() {
        composeRule.setContent {
            HorizontalGrid(
                rowCount = 3,
                modifier = Modifier.testTag("grid"),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(0.dp)
            .assertHeightIsEqualTo(0.dp)
    }

    @Test
    fun testVerticalGrid_noChild() {
        composeRule.setContent {
            VerticalGrid(
                columnCount = 3,
                modifier = Modifier.testTag("grid"),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(0.dp)
            .assertHeightIsEqualTo(0.dp)
    }

    @Test
    fun testHorizontalGrid_withSingleLineChildren() {
        val rowCount = 3
        val testTags = arrayOf("1", "2", "3")
        val childSize = 30.dp

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier.testTag("grid")
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

        for ((i, tag) in testTags.withIndex()) {
            composeRule
                .onNode(hasTestTag(tag))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = 0.dp,
                    expectedTop = childSize * i
                )
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testVerticalGrid_withSingleLineChildren() {
        val columnCount = 3
        val testTags = arrayOf("1", "2", "3")
        val childSize = 30.dp

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier.testTag("grid")
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

        for ((i, tag) in testTags.withIndex()) {
            composeRule
                .onNode(hasTestTag(tag))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = childSize * i,
                    expectedTop = 0.dp
                )
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize)
    }

    @Test
    fun testHorizontalGrid_withSameChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val childSize = 30.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier.testTag("grid")
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
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testVerticalGrid_withSameChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val childSize = 30.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier.testTag("grid")
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
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testHorizontalGrid_withFixedHeight() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridHeight = 100.dp
        val childSize = 30.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid")
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
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_withFixedWidth() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridWidth = 100.dp
        val childSize = 30.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

        composeRule.setContent {
            VerticalGrid(
                columnCount = rowCount,
                modifier = Modifier
                    .width(gridWidth)
                    .testTag("grid")
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
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testHorizontalGrid_withNotEnoughHeight() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridHeight = 45.dp
        val childSize = 30.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier
                    .height(gridHeight)
                    .testTag("grid")
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
                when (column) {
                    0 -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsDisplayed()
                            .assertWidthIsEqualTo(childSize)
                            .assertHeightIsEqualTo(childSize)
                            .assertPositionInRootIsEqualTo(
                                expectedLeft = childSize * row,
                                expectedTop = 0.dp
                            )
                    }

                    1 -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsDisplayed()
                            .assertWidthIsEqualTo(childSize)
                            .assertHeightIsEqualTo(gridHeight - childSize)
                            .assertPositionInRootIsEqualTo(
                                expectedLeft = childSize * row,
                                expectedTop = childSize * column
                            )
                    }

                    else -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsNotDisplayed()
                    }
                }
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_withNotEnoughWidth() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridWidth = 45.dp
        val childSize = 30.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

        composeRule.setContent {
            VerticalGrid(
                columnCount = rowCount,
                modifier = Modifier
                    .width(gridWidth)
                    .testTag("grid")
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
                when (row) {
                    0 -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsDisplayed()
                            .assertWidthIsEqualTo(childSize)
                            .assertHeightIsEqualTo(childSize)
                            .assertPositionInRootIsEqualTo(
                                expectedLeft = 0.dp,
                                expectedTop = childSize * column
                            )
                    }

                    1 -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsDisplayed()
                            .assertWidthIsEqualTo(gridWidth - childSize)
                            .assertHeightIsEqualTo(childSize)
                            .assertPositionInRootIsEqualTo(
                                expectedLeft = childSize * row,
                                expectedTop = childSize * column
                            )
                    }

                    else -> {
                        composeRule
                            .onNode(hasTestTag(testTags[i]))
                            .assertIsNotDisplayed()
                    }
                }
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testHorizontalGrid_withArrangementStart_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementStart_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementStart_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = gridWidth - (childSize * (row + 1)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementStart_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = gridWidth - (childSize * (row + 1)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementEnd_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = gridWidth - (childSize * (rowCount - row)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementEnd_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = gridWidth - (childSize * (rowCount - row)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementEnd_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * (rowCount - (row + 1)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementEnd_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * (rowCount - (row + 1)),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementCenter_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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

        val centerLeft = (gridWidth - (childSize * rowCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = centerLeft + (childSize * row),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementCenter_whenLtr() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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

        val centerLeft = (gridWidth - (childSize * rowCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = centerLeft + (childSize * row),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withHorizontalArrangementCenter_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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

        val centerLeft = (gridWidth - (childSize * rowCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = centerLeft + (childSize * (rowCount - (row + 1))),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withHorizontalArrangementCenter_whenRtl() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridWidth = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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

        val centerLeft = (gridWidth - (childSize * rowCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = centerLeft + (childSize * (rowCount - (row + 1))),
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementTop() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementTop() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = childSize * column
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementBottom() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = gridHeight - (childSize * (columnCount - column))
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withArrangementBottom() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = gridHeight - (childSize * (columnCount - column))
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withVerticalArrangementCenter() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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

        val centerTop = (gridHeight - (childSize * columnCount)) / 2
        var i = 0
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = centerTop + (childSize * column)
                    )
                i++
            }
        }
    }

    @Test
    fun testVerticalGrid_withVerticalArrangementCenter() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val gridHeight = 100.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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

        val centerTop = (gridHeight - (childSize * columnCount)) / 2
        var i = 0
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row,
                        expectedTop = centerTop + (childSize * column)
                    )
                i++
            }
        }
    }

    @Test
    fun testHorizontalGrid_withArrangementSpaceBy() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childSize = 20.dp
        val horizontalSpacing = 5.dp
        val verticalSpacing = 10.dp
        val rowCount = 3
        val columnCount = testTags.size / rowCount

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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row + (horizontalSpacing * row),
                        expectedTop = childSize * column + (verticalSpacing * column)
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo((childSize * columnCount) + (horizontalSpacing * (columnCount - 1)))
            .assertHeightIsEqualTo((childSize * rowCount) + (verticalSpacing * (rowCount - 1)))
    }

    @Test
    fun testVerticalGrid_withArrangementSpaceBy() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childSize = 20.dp
        val horizontalSpacing = 5.dp
        val verticalSpacing = 10.dp
        val columnCount = 3
        val rowCount = testTags.size / columnCount

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                composeRule
                    .onNode(hasTestTag(testTags[i]))
                    .assertWidthIsEqualTo(childSize)
                    .assertHeightIsEqualTo(childSize)
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * row + (horizontalSpacing * row),
                        expectedTop = childSize * column + (verticalSpacing * column)
                    )
                i++
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo((childSize * columnCount) + (horizontalSpacing * (columnCount - 1)))
            .assertHeightIsEqualTo((childSize * rowCount) + (verticalSpacing * (rowCount - 1)))
    }
}
