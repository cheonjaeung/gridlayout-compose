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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.roundToInt

@RunWith(AndroidJUnit4::class)
class GridArrangementTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testHorizontalGrid_horizontalArrangementStart_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementStart_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementStart_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementStart_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementStart_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - itemSize * (i + 1))
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementStart_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - (expectedItemWidth * (i + 1)))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementStart_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - itemSize * (i + 1))
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementStart_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Start,
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
        }

        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - itemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementCenter_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementCenter_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementCenter_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementCenter_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementCenter_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - itemSize * (i + 1) - spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementCenter_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - (expectedItemWidth * (i + 1)))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementCenter_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - (itemSize * (i + 1)) - spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementCenter_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.Center,
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
        }

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementEnd_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(spacing + itemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementEnd_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementEnd_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(spacing + itemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementEnd_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementEnd_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - spacing - itemSize * (i + 1))
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementEnd_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val expectedItemWidth = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - (expectedItemWidth * (i + 1)))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementEnd_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - spacing - itemSize * (i + 1))
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementEnd_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.End,
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
        }

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpacedBy_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i + expectedSpacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpacedBy_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        val totalSpacing = spacing * (itemCount - 1)
        val expectedItemWidth = (gridSize - totalSpacing) / itemCount
        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemWidth * i + expectedSpacing)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpacedBy_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(itemSize * i + expectedSpacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpacedBy_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        val cellCount = (gridSize + spacing).value.roundToInt() / (itemSize + spacing).value.roundToInt()
        val totalSpacing = spacing * (cellCount - 1)
        val expectedItemSize = (gridSize - totalSpacing) / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((expectedItemSize + spacing) * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpacedBy_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - expectedSpacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpacedBy_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        val totalSpacing = spacing * (itemCount - 1)
        val expectedItemWidth = (gridSize - totalSpacing) / itemCount
        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (expectedItemWidth * (i + 1)) - expectedSpacing
                )
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpacedBy_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - itemSize * (i + 1) - expectedSpacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpacedBy_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 55.dp
        val spacing = 5.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
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
        }

        val cellCount = (gridSize + spacing).value.roundToInt() / (itemSize + spacing).value.roundToInt()
        val totalSpacing = spacing * (cellCount - 1)
        val expectedItemSize = (gridSize - totalSpacing) / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - expectedItemSize * (i + 1) - (spacing * i)
                )
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceAround_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + betweenSpacing) * i + endSpacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceAround_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceAround_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + betweenSpacing) * i + endSpacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceAround_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceAround_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (betweenSpacing * i) - endSpacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceAround_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceAround_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (betweenSpacing * i) - endSpacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceAround_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceAround,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceBetween_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val betweenSpacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + betweenSpacing) * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceBetween_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceBetween_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val betweenSpacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + betweenSpacing) * i)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceBetween_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceBetween_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val betweenSpacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (betweenSpacing * i)
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceBetween_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceBetween_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val betweenSpacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (betweenSpacing * i)
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceBetween_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceEvenly_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + spacing) * i + spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceEvenly_fixed_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceEvenly_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo((itemSize + spacing) * i + spacing)
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceEvenly_adaptive_ltr() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceEvenly_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Fixed(1),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (spacing * i) - spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceEvenly_fixed_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Fixed(itemCount),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_horizontalArrangementSpaceEvenly_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                HorizontalGrid(
                    rows = SimpleGridCells.Adaptive(gridSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(
                    gridSize - (itemSize * (i + 1)) - (spacing * i) - spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_horizontalArrangementSpaceEvenly_adaptive_rtl() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                VerticalGrid(
                    columns = SimpleGridCells.Adaptive(itemSize),
                    modifier = Modifier.size(gridSize),
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
        }

        val cellCount = gridSize.value.roundToInt() / itemSize.value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertLeftPositionInRootIsEqualTo(gridSize - expectedItemSize * (i + 1))
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementTop_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Top,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementTop_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Top,
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
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementTop_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Top,
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
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementTop_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Top,
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
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementCenter_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Center,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementCenter_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Center,
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

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementCenter_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Center,
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

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementCenter_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Center,
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

        val spacing = (gridSize - (itemSize * itemCount)) / 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementBottom_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Bottom,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementBottom_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Bottom,
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

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementBottom_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Bottom,
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

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementBottom_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.Bottom,
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

        val spacing = gridSize - (itemSize * itemCount)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + spacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpacedBy_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.spacedBy(spacing),
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

        val totalSpacing = spacing * (itemCount - 1)
        val expectedItemSize = (gridSize - totalSpacing) / itemCount
        for (i in 0 until itemCount) {
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i + expectedSpacing)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpacedBy_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 60.dp
        val spacing = 5.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.spacedBy(spacing),
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
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + expectedSpacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpacedBy_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 55.dp
        val spacing = 5.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.spacedBy(spacing),
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

        val cellCount = (gridSize + spacing).value.roundToInt() / (itemSize + spacing).value.roundToInt()
        val totalSpacing = spacing * (cellCount - 1)
        val expectedItemSize = (gridSize - totalSpacing) / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((expectedItemSize + spacing) * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpacedBy_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp
        val spacing = 5.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.spacedBy(spacing),
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
            val expectedSpacing = spacing * i
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(itemSize * i + expectedSpacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceAround_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceAround,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceAround_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceAround,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + betweenSpacing) * i + endSpacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceAround_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceAround,
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

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceAround_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceAround,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val endSpacing = totalSpacing / ((itemCount - 1) * 2 + 2)
        val betweenSpacing = endSpacing * 2
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + betweenSpacing) * i + endSpacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceBetween_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceBetween,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceBetween_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceBetween,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + spacing) * i)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceBetween_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceBetween,
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

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceBetween_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceBetween,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount - 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + spacing) * i)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceEvenly_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(itemCount),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceEvenly,
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

        val expectedItemSize = gridSize / itemCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceEvenly_fixed() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(1),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceEvenly,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + spacing) * i + spacing)
        }
    }

    @Test
    fun testHorizontalGrid_verticalArrangementSpaceEvenly_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Adaptive(itemSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceEvenly,
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

        val cellCount = (gridSize).value.roundToInt() / (itemSize).value.roundToInt()
        val expectedItemSize = gridSize / cellCount
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo(expectedItemSize * i)
        }
    }

    @Test
    fun testVerticalGrid_verticalArrangementSpaceEvenly_adaptive() {
        val itemCount = 3
        val itemSize = 10.dp
        val gridSize = 50.dp

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Adaptive(gridSize),
                modifier = Modifier.size(gridSize),
                verticalArrangement = Arrangement.SpaceEvenly,
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

        val totalSpacing = gridSize - (itemSize * itemCount)
        val spacing = totalSpacing / (itemCount + 1)
        for (i in 0 until itemCount) {
            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertTopPositionInRootIsEqualTo((itemSize + spacing) * i + spacing)
        }
    }
}
