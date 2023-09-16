package com.example.compose.grid.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.woong.compose.grid.SimpleGridCells
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.Hidden,
                    skipHiddenState = false,
                )
            )

            var itemCount by remember { mutableStateOf(12) }
            var useRandomSize by remember { mutableStateOf(true) }
            var fixedCount by remember { mutableStateOf(3) }
            var adaptiveMinSize by remember { mutableStateOf(80.dp) }
            var cells: SimpleGridCells by remember { mutableStateOf(SimpleGridCells.Fixed(fixedCount)) }
            var layoutDirection: LayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
            var orientation: Orientation by remember { mutableStateOf(Orientation.Vertical) }
            var horizontalArrangement: Arrangement.Horizontal by remember {
                mutableStateOf(Arrangement.Start)
            }
            var verticalArrangement: Arrangement.Vertical by remember {
                mutableStateOf(Arrangement.Top)
            }

            val items by remember(itemCount, useRandomSize) {
                mutableStateOf(createGridItems(count = itemCount, useRandomSize = useRandomSize))
            }

            MaterialTheme {
                BottomSheetScaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    content = {
                        SampleScreen(
                            modifier = Modifier.fillMaxSize(),
                            cells = cells,
                            items = items,
                            layoutDirection = layoutDirection,
                            gridHorizontalArrangement = horizontalArrangement,
                            gridVerticalArrangement = verticalArrangement,
                            isVertical = orientation == Orientation.Vertical,
                            onButtonClick = {
                                coroutineScope.launch {
                                    if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                        scaffoldState.bottomSheetState.hide()
                                    } else {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        )
                    },
                    sheetContent = {
                        OptionsSheet(
                            itemCount = itemCount,
                            onItemCountChange = { itemCount = it },
                            useRandomSize = useRandomSize,
                            onUseRandomSizeChange = { useRandomSize = it },
                            cells = cells,
                            onCellsChange = { cells = it },
                            fixedCount = fixedCount,
                            onFixedCountChange = { fixedCount = it },
                            adaptiveMinSize = adaptiveMinSize,
                            onAdaptiveMinSizeChange = { adaptiveMinSize = it },
                            layoutDirection = layoutDirection,
                            onLayoutDirectionChange = { layoutDirection = it },
                            orientation = orientation,
                            onOrientationChange = { orientation = it },
                            horizontalArrangement = horizontalArrangement,
                            onHorizontalArrangementChange = { horizontalArrangement = it },
                            verticalArrangement = verticalArrangement,
                            onVerticalArrangementChange = { verticalArrangement = it },
                            onButtonClick = {
                                coroutineScope.launch {
                                    if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                        scaffoldState.bottomSheetState.hide()
                                    } else {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        )
                    },
                )
            }
        }
    }
}

private fun createGridItems(
    count: Int,
    useRandomSize: Boolean = false,
): List<GridItemInfo> {
    val items = mutableListOf<GridItemInfo>()
    val fixedSize = 80.dp
    for (i in 0 until count) {
        items.add(
            GridItemInfo(
                color = randomColor(),
                width = if (useRandomSize) randomSize() else fixedSize,
                height = if (useRandomSize) randomSize() else fixedSize,
            )
        )
    }
    return items
}

private fun randomColor(): Color {
    val randomRed = Random.nextInt(150, 220)
    val randomGreen = Random.nextInt(150, 220)
    val randomBlue = Random.nextInt(150, 220)
    return Color(red = randomRed, green = randomGreen, blue = randomBlue, alpha = 127)
}

private fun randomSize(): Dp {
    return Dp(Random.nextInt(60, 100).toFloat())
}
