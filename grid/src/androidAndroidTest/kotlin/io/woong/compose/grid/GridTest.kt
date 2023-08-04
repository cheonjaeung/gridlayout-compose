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
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GridTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    private val gridTag: String = "grid"

    @Test
    fun testHorizontalGrid_noItem_withFixed() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(3),
                modifier = Modifier.testTag(gridTag),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(0.dp)
    }

    @Test
    fun testVerticalGrid_noItem_withFixed() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.testTag(gridTag),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(0.dp)
    }

    @Test
    fun testHorizontalGrid_noItem_withAdaptive() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(10.dp),
                modifier = Modifier.testTag(gridTag),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(0.dp)
    }

    @Test
    fun testVerticalGrid_noItem_withAdaptive() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(10.dp),
                modifier = Modifier.testTag(gridTag),
                content = {}
            )
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(0.dp)
    }

    @Test
    fun testHorizontalGrid_oneItem_withFixed() {
        val itemTag = "1"
        val itemSize = 10.dp
        val gridHeight = 30.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(1),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertSizeIsEqualTo(expectedWidth = itemSize, expectedHeight = gridHeight)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(expectedWidth = itemSize, expectedHeight = gridHeight)
    }

    @Test
    fun testVerticalGrid_oneItem_withFixed() {
        val itemTag = "1"
        val itemSize = 10.dp
        val gridWidth = 30.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertSizeIsEqualTo(expectedWidth = gridWidth, expectedHeight = itemSize)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(expectedWidth = gridWidth, expectedHeight = itemSize)
    }

    @Test
    fun testHorizontalGrid_oneItem_withFixed_multipleCount() {
        val itemTag = "1"
        val itemSize = 10.dp
        val gridHeight = 40.dp
        val rowCount = 3

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertWidthIsEqualTo(itemSize)
            .assertHeightIsEqualTo(gridHeight / rowCount)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_oneItem_withFixed_multipleCount() {
        val itemTag = "1"
        val itemSize = 10.dp
        val gridWidth = 40.dp
        val columnCount = 3

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertWidthIsEqualTo(gridWidth / columnCount)
            .assertHeightIsEqualTo(itemSize)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize)
    }

    @Test
    fun testHorizontalGrid_oneItem_withAdaptive() {
        val itemTag = "1"
        val itemSize = 10.dp
        val minHeight = 8.dp
        val gridHeight = 30.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(minHeight),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        val expectedCellCount = expectAdaptiveGridCrossAxisCount(gridHeight, minHeight)
        val expectedItemHeight = gridHeight / expectedCellCount

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertSizeIsEqualTo(expectedWidth = itemSize, expectedHeight = expectedItemHeight)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(expectedWidth = itemSize, expectedHeight = gridHeight)
    }

    @Test
    fun testVerticalGrid_oneItem_withAdaptive() {
        val itemTag = "1"
        val itemSize = 10.dp
        val minWidth = 8.dp
        val gridWidth = 30.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(minWidth),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth),
                content = {
                    Box(
                        modifier = Modifier
                            .testTag(itemTag)
                            .size(itemSize)
                    )
                }
            )
        }

        val expectedCellCount = expectAdaptiveGridCrossAxisCount(gridWidth, minWidth)
        val expectedItemWidth = gridWidth / expectedCellCount

        composeRule
            .onNode(hasTestTag(itemTag))
            .assertSizeIsEqualTo(expectedWidth = expectedItemWidth, expectedHeight = itemSize)

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertSizeIsEqualTo(expectedWidth = gridWidth, expectedHeight = itemSize)
    }

    @Test
    fun testHorizontalGrid_oneLine_withFixed() {
        val rowCount = 3
        val gridHeight = 45.dp
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight),
                content = {
                    for (i in 0 until rowCount) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(itemSize)
                        )
                    }
                }
            )
        }

        val expectedItemHeight = gridHeight / rowCount
        for (i in 0 until rowCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertWidthIsEqualTo(itemSize)
                .assertHeightIsEqualTo(expectedItemHeight)
                .assertTopPositionInRootIsEqualTo(expectedItemHeight * i)
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_oneLine_withFixed() {
        val columnCount = 3
        val gridWidth = 45.dp
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth),
                content = {
                    for (i in 0 until columnCount) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(itemSize)
                        )
                    }
                }
            )
        }

        val expectedItemWidth = gridWidth / columnCount
        for (i in 0 until columnCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertWidthIsEqualTo(expectedItemWidth)
                .assertHeightIsEqualTo(itemSize)
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize)
    }

    @Test
    fun testHorizontalGrid_oneLine_withAdaptive() {
        val gridHeight = 30.dp
        val minHeight = 8.dp
        val itemSize = 5.dp
        val rowCount = expectAdaptiveGridCrossAxisCount(gridHeight, minHeight)

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(minHeight),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight),
                content = {
                    for (i in 0 until rowCount) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(itemSize)
                        )
                    }
                }
            )
        }

        val expectedItemHeight = gridHeight / rowCount
        for (i in 0 until rowCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertWidthIsEqualTo(itemSize)
                .assertHeightIsEqualTo(expectedItemHeight)
                .assertTopPositionInRootIsEqualTo(expectedItemHeight * i)
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_oneLine_withAdaptive() {
        val gridWidth = 30.dp
        val minWidth = 8.dp
        val itemSize = 5.dp
        val columnCount = expectAdaptiveGridCrossAxisCount(gridWidth, minWidth)

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(minWidth),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth),
                content = {
                    for (i in 0 until columnCount) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(itemSize)
                        )
                    }
                }
            )
        }

        val expectedItemWidth = gridWidth / columnCount
        for (i in 0 until columnCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertWidthIsEqualTo(expectedItemWidth)
                .assertHeightIsEqualTo(itemSize)
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize)
    }

    @Test
    fun testHorizontalGrid_breakDownToNextLine_withFixed() {
        val rowCount = 3
        val gridHeight = 45.dp
        val itemCount = rowCount + 1
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            if (i < rowCount) {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(itemSize)
                    .assertHeightIsEqualTo(gridHeight / rowCount)
                    .assertTopPositionInRootIsEqualTo((gridHeight / rowCount) * i)
            } else {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(itemSize)
                    .assertHeightIsEqualTo(gridHeight / rowCount)
                    .assertLeftPositionInRootIsEqualTo(itemSize)
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize * 2)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_breakDownToNextLine_withFixed() {
        val columnCount = 3
        val gridWidth = 45.dp
        val itemCount = columnCount + 1
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            if (i < columnCount) {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(gridWidth / columnCount)
                    .assertHeightIsEqualTo(itemSize)
                    .assertLeftPositionInRootIsEqualTo((gridWidth / columnCount) * i)
            } else {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(gridWidth / columnCount)
                    .assertHeightIsEqualTo(itemSize)
                    .assertTopPositionInRootIsEqualTo(itemSize)
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize * 2)
    }

    @Test
    fun testHorizontalGrid_breakDownToNextLine_withAdaptive() {
        val gridHeight = 30.dp
        val minHeight = 8.dp
        val itemSize = 5.dp
        val rowCount = expectAdaptiveGridCrossAxisCount(gridHeight, minHeight)
        val itemCount = rowCount + 1

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(minHeight),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        val expectedItemHeight = gridHeight / rowCount
        for (i in 0 until itemCount) {
            if (i < rowCount) {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(itemSize)
                    .assertHeightIsEqualTo(gridHeight / rowCount)
                    .assertTopPositionInRootIsEqualTo(expectedItemHeight * i)
            } else {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(itemSize)
                    .assertHeightIsEqualTo(expectedItemHeight)
                    .assertLeftPositionInRootIsEqualTo(itemSize)
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize * 2)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_breakDownToNextLine_withAdaptive() {
        val gridWidth = 30.dp
        val minWidth = 8.dp
        val itemSize = 5.dp
        val columnCount = expectAdaptiveGridCrossAxisCount(gridWidth, minWidth)
        val itemCount = columnCount + 1

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        val expectedItemWidth = gridWidth / columnCount
        for (i in 0 until itemCount) {
            if (i < columnCount) {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(gridWidth / columnCount)
                    .assertHeightIsEqualTo(itemSize)
                    .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
            } else {
                composeRule
                    .onNode(hasTestTag(i.toString()))
                    .assertWidthIsEqualTo(expectedItemWidth)
                    .assertHeightIsEqualTo(itemSize)
                    .assertTopPositionInRootIsEqualTo(itemSize)
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize * 2)
    }

    @Test
    fun testHorizontalGrid_breakDownMultipleTimes_withFixed() {
        val rowCount = 3
        val gridHeight = 45.dp
        val itemCount = rowCount * 2 + 1
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            when {
                i < rowCount -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(gridHeight / rowCount)
                        .assertTopPositionInRootIsEqualTo((gridHeight / rowCount) * i)
                }

                i < rowCount * 2 -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(gridHeight / rowCount)
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = itemSize,
                            expectedTop = (gridHeight / rowCount) * (i - rowCount)
                        )
                }

                else -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(gridHeight / rowCount)
                        .assertLeftPositionInRootIsEqualTo(itemSize * 2)
                }
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize * 3)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_breakDownMultipleTimes_withFixed() {
        val columnCount = 3
        val gridWidth = 45.dp
        val itemCount = columnCount * 2 + 1
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            when {
                i < columnCount -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(gridWidth / columnCount)
                        .assertHeightIsEqualTo(itemSize)
                        .assertLeftPositionInRootIsEqualTo((gridWidth / columnCount) * i)
                }

                i < columnCount * 2 -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(gridWidth / columnCount)
                        .assertHeightIsEqualTo(itemSize)
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = (gridWidth / columnCount) * (i - columnCount),
                            expectedTop = itemSize
                        )
                }

                else -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(gridWidth / columnCount)
                        .assertHeightIsEqualTo(itemSize)
                        .assertTopPositionInRootIsEqualTo(itemSize * 2)
                }
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize * 3)
    }

    @Test
    fun testHorizontalGrid_breakDownMultipleTimes_withAdaptive() {
        val gridHeight = 30.dp
        val minHeight = 8.dp
        val itemSize = 5.dp
        val rowCount = expectAdaptiveGridCrossAxisCount(gridHeight, minHeight)
        val itemCount = rowCount * 2 + 1

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(minHeight),
                modifier = Modifier
                    .testTag(gridTag)
                    .height(gridHeight)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        val expectedItemHeight = gridHeight / rowCount
        for (i in 0 until itemCount) {
            when {
                i < rowCount -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(expectedItemHeight)
                        .assertTopPositionInRootIsEqualTo(expectedItemHeight * i)
                }

                i < rowCount * 2 -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(expectedItemHeight)
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = itemSize,
                            expectedTop = expectedItemHeight * (i - rowCount)
                        )
                }

                else -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(itemSize)
                        .assertHeightIsEqualTo(expectedItemHeight)
                        .assertLeftPositionInRootIsEqualTo(itemSize * 2)
                }
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(itemSize * 3)
            .assertHeightIsEqualTo(gridHeight)
    }

    @Test
    fun testVerticalGrid_breakDownMultipleTimes_withAdaptive() {
        val gridWidth = 30.dp
        val minWidth = 8.dp
        val itemSize = 5.dp
        val columnCount = expectAdaptiveGridCrossAxisCount(gridWidth, minWidth)
        val itemCount = columnCount * 2 + 1

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(minWidth),
                modifier = Modifier
                    .testTag(gridTag)
                    .width(gridWidth)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                    )
                }
            }
        }

        val expectedItemWidth = gridWidth / columnCount
        for (i in 0 until itemCount) {
            when {
                i < columnCount -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(expectedItemWidth)
                        .assertHeightIsEqualTo(itemSize)
                        .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
                }

                i < columnCount * 2 -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(expectedItemWidth)
                        .assertHeightIsEqualTo(itemSize)
                        .assertPositionInRootIsEqualTo(
                            expectedLeft = expectedItemWidth * (i - columnCount),
                            expectedTop = itemSize
                        )
                }

                else -> {
                    composeRule
                        .onNode(hasTestTag(i.toString()))
                        .assertWidthIsEqualTo(gridWidth / columnCount)
                        .assertHeightIsEqualTo(itemSize)
                        .assertTopPositionInRootIsEqualTo(itemSize * 2)
                }
            }
        }

        composeRule
            .onNode(hasTestTag(gridTag))
            .assertWidthIsEqualTo(gridWidth)
            .assertHeightIsEqualTo(itemSize * 3)
    }

    @Test
    fun testHorizontalGrid_notEnoughLayoutSize_withFixed() {
        val rowCount = 2
        val gridSize = 30.dp
        val itemSize = 35.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.size(gridSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertIsDisplayed()
            .assertHeightIsEqualTo(gridSize / rowCount)

        composeRule
            .onNode(hasTestTag("1"))
            .assertIsDisplayed()
            .assertHeightIsEqualTo(gridSize / rowCount)

        composeRule
            .onNode(hasTestTag("2"))
            .assertIsNotDisplayed()

        composeRule
            .onNode(hasTestTag("3"))
            .assertIsNotDisplayed()
    }

    @Test
    fun testVerticalGrid_notEnoughLayoutSize_withFixed() {
        val columnCount = 2
        val gridSize = 30.dp
        val itemSize = 35.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier.size(gridSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertIsDisplayed()
            .assertWidthIsEqualTo(gridSize / columnCount)

        composeRule
            .onNode(hasTestTag("1"))
            .assertIsDisplayed()
            .assertWidthIsEqualTo(gridSize / columnCount)

        composeRule
            .onNode(hasTestTag("2"))
            .assertIsNotDisplayed()

        composeRule
            .onNode(hasTestTag("3"))
            .assertIsNotDisplayed()
    }


    @Test
    fun testHorizontalGrid_notEnoughLayoutSize_withAdaptive() {
        val gridSize = 30.dp
        val itemSize = 35.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(minSize = itemSize),
                modifier = Modifier.size(gridSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertIsDisplayed()

        composeRule
            .onNode(hasTestTag("1"))
            .assertIsNotDisplayed()
    }

    @Test
    fun testVerticalGrid_notEnoughLayoutSize_withAdaptive() {
        val gridSize = 30.dp
        val itemSize = 35.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(minSize = itemSize),
                modifier = Modifier.size(gridSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertIsDisplayed()

        composeRule
            .onNode(hasTestTag("1"))
            .assertIsNotDisplayed()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_withFixed_minusCountIsNotAllowed() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(-3),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_withFixed_minusCountIsNotAllowed() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(-3),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_withAdaptive_minusSizeIsNotAllowed() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive((-10).dp),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_withAdaptive_minusSizeIsNotAllowed() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive((-10).dp),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_withFixed_zeroCountIsNotAllowed() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(0),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_withFixed_zeroCountIsNotAllowed() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(0),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_withAdaptive_zeroSizeIsNotAllowed() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(0.dp),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_withAdaptive_zeroSizeIsNotAllowed() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(0.dp),
                modifier = Modifier.testTag(gridTag)
            ) {
                for (i in 0 until 6) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                    )
                }
            }
        }
    }
}
