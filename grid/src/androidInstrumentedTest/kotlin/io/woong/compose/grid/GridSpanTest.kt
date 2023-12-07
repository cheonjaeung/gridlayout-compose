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
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GridSpanTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testHorizontalGrid_noSpan() {
        val rowCount = 3
        val columnCount = 3
        val itemCount = rowCount * columnCount
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.size(itemSize * columnCount, itemSize * rowCount)
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
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testVerticalGrid_noSpan() {
        val rowCount = 3
        val columnCount = 3
        val itemCount = rowCount * columnCount
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.size(itemSize * columnCount, itemSize * rowCount)
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
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_negativeSpan() {
        composeRule.setContent {
            HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
                for (i in 0 until 9) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                            .span(-1)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_negativeSpan() {
        composeRule.setContent {
            VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
                for (i in 0 until 9) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                            .span(-1)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHorizontalGrid_zeroSpan() {
        composeRule.setContent {
            HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
                for (i in 0 until 9) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                            .span(0)
                    )
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerticalGrid_zeroSpan() {
        composeRule.setContent {
            VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
                for (i in 0 until 9) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(10.dp)
                            .span(0)
                    )
                }
            }
        }
    }

    @Test
    fun testHorizontalGrid_oneSpan() {
        val rowCount = 3
        val columnCount = 3
        val itemCount = rowCount * columnCount
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.size(itemSize * columnCount, itemSize * rowCount)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .span(1)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testVerticalGrid_oneSpan() {
        val rowCount = 3
        val columnCount = 3
        val itemCount = rowCount * columnCount
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.size(itemSize * columnCount, itemSize * rowCount)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .span(1)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_breakDown() {
        val rowCount = 3
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.height(itemSize * rowCount)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span(2)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("1"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize
            )
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize,
                expectedTop = 0.dp
            )
    }

    @Test
    fun testVerticalGrid_breakDown() {
        val columnCount = 3
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier.width(itemSize * columnCount)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span(2)
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("1"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize
            )
    }

    @Test
    fun testHorizontalGrid_fullSpan() {
        val rowCount = 3
        val itemCount = 4
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.height(itemSize * rowCount)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .span(rowCount)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * i,
                    expectedTop = 0.dp
                )
        }
    }

    @Test
    fun testVerticalGrid_fullSpan() {
        val columnCount = 3
        val itemCount = 4
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier.width(itemSize * columnCount)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .span(columnCount)
                    )
                }
            }
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = 0.dp,
                    expectedTop = itemSize * i
                )
        }
    }

    @Test
    fun testHorizontalGrid_overSpan() {
        val rowCount = 3
        val itemSize = 10.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier.height(itemSize * rowCount)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span(100)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span(2)
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
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("1"))
            .assertIsNotDisplayed()
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize,
                expectedTop = 0.dp
            )
    }

    @Test
    fun testVerticalGrid_overSpan() {
        val columnCount = 3
        val itemSize = 10.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier.width(itemSize * columnCount)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span(1)
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span(100)
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span(2)
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
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("1"))
            .assertIsNotDisplayed()
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize
            )
    }
}
