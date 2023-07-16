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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
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
    fun testHorizontalGrid_singleLine() {
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
    fun testVerticalGrid_singleLine() {
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
    fun testHorizontalGrid_breakDownToNextLine() {
        val rowCount = 3
        val testTags = arrayOf("1", "2", "3", "4")
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
            if (i < 3) {
                // First line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = 0.dp,
                        expectedTop = childSize * i
                    )
            } else {
                // Next line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize,
                        expectedTop = childSize * (i - 3)
                    )
            }
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * 2)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testVerticalGrid_breakDownToNextLine() {
        val columnCount = 3
        val testTags = arrayOf("1", "2", "3", "4", "5")
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
            if (i < 3) {
                // First line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * i,
                        expectedTop = 0.dp
                    )
            } else {
                // Next line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * (i - 3),
                        expectedTop = childSize
                    )
            }
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * 2)
    }

    @Test
    fun testHorizontalGrid_breakDownMultipleTimes() {
        val rowCount = 3
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7")
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
            if (i < 3) {
                // First line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = 0.dp,
                        expectedTop = childSize * i
                    )
            } else if (i < 6) {
                // Second line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize,
                        expectedTop = childSize * (i - 3)
                    )
            } else {
                // Last line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * 2,
                        expectedTop = childSize * (i - 6)
                    )
            }
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * 3)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testVerticalGrid_breakDownMultipleTimes() {
        val columnCount = 3
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7")
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
            if (i < 3) {
                // First line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * i,
                        expectedTop = 0.dp
                    )
            } else if (i < 6) {
                // Second line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * (i - 3),
                        expectedTop = childSize
                    )
            } else {
                // Last line
                composeRule
                    .onNode(hasTestTag(tag))
                    .assertPositionInRootIsEqualTo(
                        expectedLeft = childSize * (i - 6),
                        expectedTop = childSize * 2
                    )
            }
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * 3)
    }

    @Test
    fun testHorizontalGrid_sameChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val childSize = 30.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

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
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testVerticalGrid_sameChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val childSize = 30.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

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
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testHorizontalGrid_variousChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childrenSize = arrayOf(
            30.dp, 40.dp, 50.dp,
            50.dp, 30.dp, 40.dp,
            40.dp, 50.dp, 30.dp,
        )
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

        composeRule.setContent {
            HorizontalGrid(
                rowCount = rowCount,
                modifier = Modifier.testTag("grid")
            ) {
                for (index in testTags.indices) {
                    Box(
                        modifier = Modifier
                            .size(childrenSize[index])
                            .testTag(testTags[index])
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
                        .assertWidthIsEqualTo(childrenSize[i])
                        .assertHeightIsEqualTo(childrenSize[i])
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = 50.dp * column,
                            expectedTop = 50.dp * row
                        )
                    i++
                }
            }
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(150.dp)
            .assertHeightIsEqualTo(150.dp)
    }

    @Test
    fun testVerticalGrid_variousChildrenSizes() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val childrenSize = arrayOf(
            30.dp, 40.dp, 50.dp,
            50.dp, 30.dp, 40.dp,
            40.dp, 50.dp, 30.dp,
        )
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
                modifier = Modifier.testTag("grid")
            ) {
                for (index in testTags.indices) {
                    Box(
                        modifier = Modifier
                            .size(childrenSize[index])
                            .testTag(testTags[index])
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
                        .assertWidthIsEqualTo(childrenSize[i])
                        .assertHeightIsEqualTo(childrenSize[i])
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = 50.dp * column,
                            expectedTop = 50.dp * row
                        )
                    i++
                }
            }
        }

        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(150.dp)
            .assertHeightIsEqualTo(150.dp)
    }

    @Test
    fun testHorizontalGrid_fixedHeight() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridHeight = 100.dp
        val childSize = 30.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

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
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_fixedWidth() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val gridWidth = 100.dp
        val childSize = 30.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
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
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(childSize * rowCount)
    }

    @Test
    fun testHorizontalGrid_notEnoughHeight() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6")
        val gridHeight = 35.dp
        val childSize = 20.dp
        val rowCount = 3
        val columnCount = TestUtils.computeMainAxisCount(testTags.size, rowCount)

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
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                if (i < testTags.size) {
                    when (row) {
                        0 -> {
                            composeRule
                                .onNode(hasTestTag(testTags[i]))
                                .assertIsDisplayed()
                                .assertWidthIsEqualTo(childSize)
                                .assertHeightIsEqualTo(childSize)
                                .assertPositionInRootIsEqualTo(
                                    expectedLeft = childSize * column,
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
                                    expectedLeft = childSize * column,
                                    expectedTop = childSize * row
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
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(childSize * columnCount)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_notEnoughWidth() {
        val testTags = arrayOf("1", "2", "3", "4", "5", "6")
        val gridWidth = 35.dp
        val childSize = 20.dp
        val columnCount = 3
        val rowCount = TestUtils.computeMainAxisCount(testTags.size, columnCount)

        composeRule.setContent {
            VerticalGrid(
                columnCount = columnCount,
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
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                if (i < testTags.size) {
                    when (column) {
                        0 -> {
                            composeRule
                                .onNode(hasTestTag(testTags[i]))
                                .assertIsDisplayed()
                                .assertWidthIsEqualTo(childSize)
                                .assertHeightIsEqualTo(childSize)
                                .assertPositionInRootIsEqualTo(
                                    expectedLeft = 0.dp,
                                    expectedTop = childSize * row
                                )
                        }

                        1 -> {
                            composeRule
                                .onNode(hasTestTag(testTags[i]))
                                .assertIsDisplayed()
                                .assertWidthIsEqualTo(gridWidth - childSize)
                                .assertHeightIsEqualTo(childSize)
                                .assertPositionInRootIsEqualTo(
                                    expectedLeft = childSize * column,
                                    expectedTop = childSize * row
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
        }
        composeRule
            .onNode(hasTestTag("grid"))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(childSize * rowCount)
    }
}
