package com.cheonjaeung.compose.grid.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.ceil

/**
 * Benchmarks comparing [VerticalGrid], [LazyVerticalGrid], and a [Row]+[Column] grid.
 *
 * Three comparisons:
 * - A) [VerticalGrid] (no spacing) vs [RowColumnGridWithoutSpacing]
 * - B) [VerticalGrid] with spacing vs [RowColumnGridWithSpacing]
 * - C) [LazyVerticalGrid] vs [VerticalGrid] in two scenarios:
 *   - `_bounded`: both grids placed in [fillMaxSize] viewport; [LazyVerticalGrid] virtualizes items
 *     outside the viewport while [VerticalGrid] measures all items eagerly.
 *   - `_allVisible`: [LazyVerticalGrid] given a fixed height large enough to show every item,
 *     forcing full composition for a pure composition/measure cost comparison.
 *
 * Run with:
 * ```
 * ./gradlew :benchmark:connectedAndroidTest
 * ```
 *
 * Results are saved to:
 * ```
 * benchmark/build/outputs/connected_android_test_additional_output/releaseAndroidTest/connected/<device>/
 * ```
 * as a JSON file.
 */
@RunWith(AndroidJUnit4::class)
class GridBenchmark {

    @get:Rule(order = 1)
    val benchmarkRule = BenchmarkRule()

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Test
    fun verticalGrid_fixed3_9items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(9) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun verticalGrid_fixed3_30items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(30) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun verticalGrid_fixed3_100items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(100) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun rowColumnNoSpacer_3cols_9items() {
        benchmarkContent {
            RowColumnGridWithoutSpacing(itemCount = 9, columnCount = 3)
        }
    }

    @Test
    fun rowColumnNoSpacer_3cols_30items() {
        benchmarkContent {
            RowColumnGridWithoutSpacing(itemCount = 30, columnCount = 3)
        }
    }

    @Test
    fun rowColumnNoSpacer_3cols_100items() {
        benchmarkContent {
            RowColumnGridWithoutSpacing(itemCount = 100, columnCount = 3)
        }
    }

    @Test
    fun verticalGridWithSpacing_fixed3_9items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(9) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun verticalGridWithSpacing_fixed3_30items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(30) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun verticalGridWithSpacing_fixed3_100items() {
        benchmarkContent {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(100) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_9items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(List(9) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_30items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(List(30) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_100items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(List(100) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_9items_allVisible() {
        // 3 rows × 50.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(150.dp),
            ) {
                items(List(9) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_30items_allVisible() {
        // 10 rows × 50.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(500.dp),
            ) {
                items(List(30) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGrid_fixed3_100items_allVisible() {
        // 34 rows × 50.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(1700.dp),
            ) {
                items(List(100) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_9items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(9) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_30items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(30) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_100items_bounded() {
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(100) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_9items_allVisible() {
        // 3 rows × 50.dp + 2 gaps × 8.dp = 166.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(166.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(9) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_30items_allVisible() {
        // 10 rows × 50.dp + 9 gaps × 8.dp = 572.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(572.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(30) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun lazyVerticalGridWithSpacing_fixed3_100items_allVisible() {
        // 34 rows × 50.dp + 33 gaps × 8.dp = 1964.dp
        benchmarkContent {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(1964.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(List(100) { it }) { Box(modifier = Modifier.height(50.dp)) }
            }
        }
    }

    @Test
    fun rowColumnWithSpacing_3cols_9items() {
        benchmarkContent {
            RowColumnGridWithSpacing(itemCount = 9, columnCount = 3)
        }
    }

    @Test
    fun rowColumnWithSpacing_3cols_30items() {
        benchmarkContent {
            RowColumnGridWithSpacing(itemCount = 30, columnCount = 3)
        }
    }

    @Test
    fun rowColumnWithSpacing_3cols_100items() {
        benchmarkContent {
            RowColumnGridWithSpacing(itemCount = 100, columnCount = 3)
        }
    }

    private fun benchmarkContent(content: @Composable () -> Unit) {
        var isVisible by mutableStateOf(false)

        composeTestRule.setContent {
            if (isVisible) content()
        }
        composeTestRule.waitForIdle()

        benchmarkRule.measureRepeated {
            isVisible = true
            composeTestRule.waitForIdle()

            runWithMeasurementDisabled {
                isVisible = false
                composeTestRule.waitForIdle()
            }
        }
    }
}

@Composable
private fun RowColumnGridWithoutSpacing(
    itemCount: Int,
    columnCount: Int,
) {
    val rowCount = ceil(itemCount.toDouble() / columnCount).toInt()

    Column(modifier = Modifier.fillMaxWidth()) {
        for (rowIndex in 0 until rowCount) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (colIndex in 0 until columnCount) {
                    val itemIndex = rowIndex * columnCount + colIndex
                    if (itemIndex < itemCount) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .height(50.dp))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun RowColumnGridWithSpacing(
    itemCount: Int,
    columnCount: Int,
    spacing: Dp = 8.dp,
) {
    val rowCount = ceil(itemCount.toDouble() / columnCount).toInt()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing),
    ) {
        for (rowIndex in 0 until rowCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing),
            ) {
                for (colIndex in 0 until columnCount) {
                    val itemIndex = rowIndex * columnCount + colIndex
                    if (itemIndex < itemCount) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .height(50.dp))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
