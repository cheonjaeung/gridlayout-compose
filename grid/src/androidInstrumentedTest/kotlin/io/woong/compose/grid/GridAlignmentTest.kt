package io.woong.compose.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GridAlignmentTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    private val gridSize = 120.dp
    private val itemSize = 20.dp

    private val rowCount = 3
    private val columnCount = 3
    private val itemCount = rowCount * columnCount

    @Test
    fun testHorizontalGrid_alignTopStart() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopStart)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex
                )
        }
    }

    @Test
    fun testVerticalGrid_alignTopStart() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopStart)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignTopCenter() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex
                )
        }
    }

    @Test
    fun testVerticalGrid_alignTopCenter() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = (cellWidth - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignTopEnd() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex
                )
        }
    }

    @Test
    fun testVerticalGrid_alignTopEnd() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = cellWidth - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignCenterStart() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.CenterStart)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = (cellHeight - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignCenterStart() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.CenterStart)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignCenter() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = (cellHeight - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignCenter() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = (cellWidth - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignCenterEnd() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = (cellHeight - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignCenterEnd() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = cellWidth - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignBottomStart() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomStart)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = cellHeight - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignBottomStart() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomStart)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignBottomCenter() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = cellHeight - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignBottomCenter() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = (cellWidth - itemSize) / 2

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }

    @Test
    fun testHorizontalGrid_alignBottomEnd() {
        composeRule.setContent {
            HorizontalGrid(
                rows = SimpleGridCells.Fixed(rowCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }

        val cellHeight = gridSize / rowCount
        val spacing = cellHeight - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i % rowCount
            val columnIndex = i / columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = itemSize * columnIndex,
                    expectedTop = cellHeight * rowIndex + spacing
                )
        }
    }

    @Test
    fun testVerticalGrid_alignBottomEnd() {
        composeRule.setContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(columnCount, false),
                modifier = Modifier.size(gridSize)
            ) {
                for (i in 0 until itemCount) {
                    Box(
                        modifier = Modifier
                            .testTag(i.toString())
                            .size(itemSize)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }

        val cellWidth = gridSize / columnCount
        val spacing = cellWidth - itemSize

        for (i in 0 until itemCount) {
            val rowIndex = i / rowCount
            val columnIndex = i % columnCount

            composeRule
                .onNode(hasTestTag(i.toString()))
                .assertPositionInRootIsEqualTo(
                    expectedLeft = cellWidth * columnIndex + spacing,
                    expectedTop = itemSize * rowIndex
                )
        }
    }
}
