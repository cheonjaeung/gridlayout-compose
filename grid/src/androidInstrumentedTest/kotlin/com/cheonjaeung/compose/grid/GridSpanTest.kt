package com.cheonjaeung.compose.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalTestApi::class)
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

    @Test
    fun testHorizontalGrid_nullSpan() {
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
                            .span(null)
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
    fun testVerticalGrid_nullSpan() {
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
                            .span(null)
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

    @Test(expected = Exception::class)
    fun testHorizontalGrid_negativeSpan() = runBlocking {
        runComposeUiTest {
            composeRule.setContent {
                HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(10.dp)
                                .span { -1 }
                        )
                    }
                }
            }
        }
    }

    @Test(expected = Exception::class)
    fun testVerticalGrid_negativeSpan() = runBlocking {
        runComposeUiTest {
            composeRule.setContent {
                VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(10.dp)
                                .span { -1 }
                        )
                    }
                }
            }
        }
    }

    @Test(expected = Exception::class)
    fun testHorizontalGrid_zeroSpan() = runBlocking {
        runComposeUiTest {
            composeRule.setContent {
                HorizontalGrid(rows = SimpleGridCells.Fixed(3)) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(10.dp)
                                .span { 0 }
                        )
                    }
                }
            }
        }
    }

    @Test(expected = Exception::class)
    fun testVerticalGrid_zeroSpan() = runBlocking {
        runComposeUiTest {
            composeRule.setContent {
                VerticalGrid(columns = SimpleGridCells.Fixed(3)) {
                    for (i in 0 until 9) {
                        Box(
                            modifier = Modifier
                                .testTag(i.toString())
                                .size(10.dp)
                                .span { 0 }
                        )
                    }
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
                            .span { 1 }
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
                            .span { 1 }
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
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
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
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
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
    fun testHorizontalGrid_maxLineSpan() {
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
                            .span {
                                assertEquals(rowCount, maxLineSpan)
                                maxLineSpan
                            }
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
    fun testVerticalGrid_maxLineSpan() {
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
                            .span {
                                assertEquals(columnCount, maxLineSpan)
                                maxLineSpan
                            }
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
    fun testHorizontalGrid_maxCurrentLineSpan() {
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
                        .span {
                            assertEquals(rowCount, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span {
                            assertEquals(rowCount - 1, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span {
                            assertEquals(rowCount - 2, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                        .span {
                            assertEquals(rowCount, maxCurrentLineSpan)
                            1
                        }
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
                expectedLeft = 0.dp,
                expectedTop = itemSize * 2
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize,
                expectedTop = 0.dp
            )
    }

    @Test
    fun testVerticalGrid_maxCurrentLineSpan() {
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
                        .span {
                            assertEquals(columnCount, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span {
                            assertEquals(columnCount - 1, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span {
                            assertEquals(columnCount - 2, maxCurrentLineSpan)
                            1
                        }
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                        .span {
                            assertEquals(columnCount, maxCurrentLineSpan)
                            1
                        }
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
                expectedLeft = itemSize * 2,
                expectedTop = 0.dp
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize
            )
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
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { maxLineSpan + 100 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
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
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { maxLineSpan + 100 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
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

    @Test
    fun testHorizontalGrid_spanWithSpacedBy() {
        val rowCount = 3
        val itemSize = 20.dp
        val spacingSize = 5.dp
        val gridHeight = itemSize * rowCount + spacingSize * (rowCount - 1)

        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount),
                modifier = Modifier
                    .testTag("grid")
                    .height(gridHeight),
                horizontalArrangement = Arrangement.spacedBy(spacingSize),
                verticalArrangement = Arrangement.spacedBy(spacingSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { 2 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                        .span { 1 }
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
            .assertSizeIsEqualTo(expectedSize = itemSize)
        composeRule
            .onNode(hasTestTag("1"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize + spacingSize
            )
            .assertSizeIsEqualTo(
                expectedWidth = itemSize,
                expectedHeight = itemSize * 2 + spacingSize
            )
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize + spacingSize,
                expectedTop = 0.dp
            )
            .assertSizeIsEqualTo(
                expectedWidth = itemSize,
                expectedHeight = itemSize * 2 + spacingSize
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize + spacingSize,
                expectedTop = (itemSize * 2 + spacingSize) + spacingSize
            )
            .assertSizeIsEqualTo(expectedSize = itemSize)

        composeRule
            .onNode(hasTestTag("grid"))
            .assertSizeIsEqualTo(
                expectedWidth = itemSize * 2 + spacingSize,
                expectedHeight = gridHeight
            )
    }

    @Test
    fun testVerticalGrid_spanWithSpacedBy() {
        val columnCount = 3
        val itemSize = 20.dp
        val spacingSize = 5.dp
        val gridWidth = itemSize * columnCount + spacingSize * (columnCount - 1)

        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount),
                modifier = Modifier
                    .testTag("grid")
                    .width(gridWidth),
                horizontalArrangement = Arrangement.spacedBy(spacingSize),
                verticalArrangement = Arrangement.spacedBy(spacingSize)
            ) {
                Box(
                    modifier = Modifier
                        .testTag("0")
                        .size(itemSize)
                        .span { 1 }
                )
                Box(
                    modifier = Modifier
                        .testTag("1")
                        .size(itemSize)
                        .span { 2 }
                )
                Box(
                    modifier = Modifier
                        .testTag("2")
                        .size(itemSize)
                        .span { 2 }
                )
                Box(
                    modifier = Modifier
                        .testTag("3")
                        .size(itemSize)
                        .span { 1 }
                )
            }
        }

        composeRule
            .onNode(hasTestTag("0"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = 0.dp
            )
            .assertSizeIsEqualTo(expectedSize = itemSize)
        composeRule
            .onNode(hasTestTag("1"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = itemSize + spacingSize,
                expectedTop = 0.dp
            )
            .assertSizeIsEqualTo(
                expectedWidth = itemSize * 2 + spacingSize,
                expectedHeight = itemSize
            )
        composeRule
            .onNode(hasTestTag("2"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = 0.dp,
                expectedTop = itemSize + spacingSize
            )
            .assertSizeIsEqualTo(
                expectedWidth = itemSize * 2 + spacingSize,
                expectedHeight = itemSize
            )
        composeRule
            .onNode(hasTestTag("3"))
            .assertPositionInRootIsEqualTo(
                expectedLeft = (itemSize * 2 + spacingSize) + spacingSize,
                expectedTop = itemSize + spacingSize
            )
            .assertSizeIsEqualTo(expectedSize = itemSize)

        composeRule
            .onNode(hasTestTag("grid"))
            .assertSizeIsEqualTo(
                expectedWidth = gridWidth,
                expectedHeight = itemSize * 2 + spacingSize
            )
    }
}
