package com.cheonjaeung.compose.grid.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val COLUMNS = 3
private val ITEM_HEIGHT = 50.dp
private val SPACING = 8.dp

/**
 * Benchmarks for first-composition cost: [VerticalGrid] vs [LazyVerticalGrid] vs [Row]+[Column].
 *
 * It can answer to two questions:
 * - How does [VerticalGrid] compare to [Row]+[Column] grid?
 * - How does [VerticalGrid] compare to [LazyVerticalGrid] when both must compose all items?
 *
 * [LazyVerticalGrid] is given an exact height that fits every item (all-visible),
 * so it cannot virtualize and the comparison is like-for-like on composition + measure cost.
 *
 * All item counts are multiples of [COLUMNS] so no placeholder Spacers are needed
 * in [RowColumnCase] — both contestants see exactly the same number of composables.
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
 */
@RunWith(AndroidJUnit4::class)
class GridBenchmark {

    @get:Rule(order = 1)
    val benchmarkRule = BenchmarkRule()

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Test
    fun verticalGrid_9() {
        benchmarkContent { VerticalGridCase(itemCount = 9) }
    }

    @Test
    fun verticalGrid_30() {
        benchmarkContent { VerticalGridCase(itemCount = 30) }
    }

    @Test
    fun verticalGrid_60() {
        benchmarkContent { VerticalGridCase(itemCount = 60) }
    }

    @Test
    fun lazyVerticalGrid_9() {
        benchmarkContent { LazyVerticalGridCase(itemCount = 9) }
    }

    @Test
    fun lazyVerticalGrid_30() {
        benchmarkContent { LazyVerticalGridCase(itemCount = 30) }
    }

    @Test
    fun lazyVerticalGrid_60() {
        benchmarkContent { LazyVerticalGridCase(itemCount = 60) }
    }

    @Test
    fun rowColumn_9() {
        benchmarkContent { RowColumnCase(itemCount = 9) }
    }

    @Test
    fun rowColumn_30() {
        benchmarkContent { RowColumnCase(itemCount = 30) }
    }

    @Test
    fun rowColumn_60() {
        benchmarkContent { RowColumnCase(itemCount = 60) }
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
private fun VerticalGridCase(itemCount: Int) {
    VerticalGrid(
        columns = SimpleGridCells.Fixed(COLUMNS),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SPACING),
        verticalArrangement = Arrangement.spacedBy(SPACING),
    ) {
        repeat(itemCount) { Box(modifier = Modifier.height(ITEM_HEIGHT)) }
    }
}

@Composable
private fun LazyVerticalGridCase(itemCount: Int) {
    val rows = itemCount / COLUMNS
    val gridHeight = ITEM_HEIGHT * rows + SPACING * (rows - 1)
    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMNS),
        modifier = Modifier.height(gridHeight),
        horizontalArrangement = Arrangement.spacedBy(SPACING),
        verticalArrangement = Arrangement.spacedBy(SPACING),
    ) {
        items(List(itemCount) { it }) { Box(modifier = Modifier.height(ITEM_HEIGHT)) }
    }
}

@Composable
private fun RowColumnCase(itemCount: Int) {
    val rowCount = itemCount / COLUMNS
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SPACING),
    ) {
        (0 until rowCount).forEach { _ ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SPACING),
            ) {
                (0 until COLUMNS).forEach { _ ->
                    Box(modifier = Modifier.weight(1f).height(ITEM_HEIGHT))
                }
            }
        }
    }
}
