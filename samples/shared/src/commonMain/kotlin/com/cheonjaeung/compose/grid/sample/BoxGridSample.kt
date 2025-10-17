package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.BoxGrid
import com.cheonjaeung.compose.grid.ExperimentalGridApi
import com.cheonjaeung.compose.grid.SimpleGridCells
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BoxGridSample(onBack: () -> Unit) {
    var gridItemCount by remember { mutableStateOf(8) }
    val gridItems by remember(gridItemCount) {
        derivedStateOf {
            List(gridItemCount) { randomColor() }
        }
    }

    var isRowsFixed by remember { mutableStateOf(true) }
    var isColumnsFixed by remember { mutableStateOf(true) }
    var rowsFixedCount by remember { mutableStateOf(5) }
    var columnsFixedCount by remember { mutableStateOf(5) }
    var rowsAdaptiveSize by remember { mutableStateOf(100.dp) }
    var columnsAdaptiveSize by remember { mutableStateOf(100.dp) }
    var isRowsFill by remember { mutableStateOf(true) }
    var isColumnsFill by remember { mutableStateOf(true) }
    val rows by remember(isRowsFixed, rowsFixedCount, rowsAdaptiveSize, isRowsFill) {
        derivedStateOf {
            if (isRowsFixed) {
                SimpleGridCells.Fixed(rowsFixedCount, isRowsFill)
            } else {
                SimpleGridCells.Adaptive(rowsAdaptiveSize, isRowsFill)
            }
        }
    }
    val columns by remember(isColumnsFixed, columnsFixedCount, columnsAdaptiveSize, isColumnsFill) {
        derivedStateOf {
            if (isColumnsFixed) {
                SimpleGridCells.Fixed(columnsFixedCount, isColumnsFill)
            } else {
                SimpleGridCells.Adaptive(columnsAdaptiveSize, isColumnsFill)
            }
        }
    }

    var horizontalSpacing by remember { mutableStateOf(0.dp) }
    var verticalSpacing by remember { mutableStateOf(0.dp) }

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
        PlatformBackHandler {
            coroutineScope.launch {
                scaffoldState.bottomSheetState.partialExpand()
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            SampleTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Box Grid",
                onBack = onBack
            )
        },
        sheetContent = {
            SheetContent(
                gridItemCount = gridItemCount,
                onGridItemCountChange = { gridItemCount = it },
                isRowsFixed = isRowsFixed,
                onRowsFixedChange = { isRowsFixed = it },
                isColumnsFixed = isColumnsFixed,
                onColumnsFixedChange = { isColumnsFixed = it },
                rowsFixedCount = rowsFixedCount,
                onRowsFixedCountChange = { rowsFixedCount = it },
                columnsFixedCount = columnsFixedCount,
                onColumnsFixedCountChange = { columnsFixedCount = it },
                rowsAdaptiveSize = rowsAdaptiveSize,
                onRowsAdaptiveSizeChange = { rowsAdaptiveSize = it },
                columnsAdaptiveSize = columnsAdaptiveSize,
                onColumnsAdaptiveSizeChange = { columnsAdaptiveSize = it },
                isRowsFill = isRowsFill,
                onRowsFillChange = { isRowsFill = it },
                isColumnsFill = isColumnsFill,
                onColumnsFillChange = { isColumnsFill = it },
                horizontalSpacing = horizontalSpacing,
                onHorizontalSpacingChange = { horizontalSpacing = it },
                verticalSpacing = verticalSpacing,
                onVerticalSpacingChange = { verticalSpacing = it }
            )
        }
    ) { inset ->
        SampleContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(inset),
            rows = rows,
            columns = columns,
            items = gridItems,
            horizontalSpacing = horizontalSpacing,
            verticalSpacing = verticalSpacing
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@OptIn(ExperimentalGridApi::class)
@Composable
private fun SampleContent(
    rows: SimpleGridCells,
    columns: SimpleGridCells,
    items: List<Color>,
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    modifier: Modifier = Modifier
) {
    BoxGrid(
        modifier = modifier,
        rows = rows,
        columns = columns,
        horizontalSpacing = horizontalSpacing,
        verticalSpacing = verticalSpacing
    ) {
        items.forEachIndexed { index, color ->
            SampleItem(
                modifier = Modifier.size(50.dp),
                color = color,
                text = index.toString()
            )
        }
    }
}

@Composable
private fun SheetContent(
    gridItemCount: Int,
    onGridItemCountChange: (Int) -> Unit,
    isRowsFixed: Boolean,
    onRowsFixedChange: (Boolean) -> Unit,
    isColumnsFixed: Boolean,
    onColumnsFixedChange: (Boolean) -> Unit,
    rowsFixedCount: Int,
    onRowsFixedCountChange: (Int) -> Unit,
    columnsFixedCount: Int,
    onColumnsFixedCountChange: (Int) -> Unit,
    rowsAdaptiveSize: Dp,
    onRowsAdaptiveSizeChange: (Dp) -> Unit,
    columnsAdaptiveSize: Dp,
    onColumnsAdaptiveSizeChange: (Dp) -> Unit,
    isRowsFill: Boolean,
    onRowsFillChange: (Boolean) -> Unit,
    isColumnsFill: Boolean,
    onColumnsFillChange: (Boolean) -> Unit,
    horizontalSpacing: Dp,
    onHorizontalSpacingChange: (Dp) -> Unit,
    verticalSpacing: Dp,
    onVerticalSpacingChange: (Dp) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            Text(text = "Item Count: $gridItemCount")
        }

        item {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..12f,
                steps = 11,
                value = gridItemCount.toFloat(),
                onValueChange = { onGridItemCountChange(it.toInt()) }
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )
        }

        item {
            Text(text = "Rows")
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Fixed",
                    selected = isRowsFixed,
                    onClick = { onRowsFixedChange(true) }
                )
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Adaptive",
                    selected = !isRowsFixed,
                    onClick = { onRowsFixedChange(false) }
                )
            }
        }

        item {
            if (isRowsFixed) {
                Text(text = "Fixed Cell Count: $rowsFixedCount")
            } else {
                Text(text = "Adaptive Minimum Size: $rowsAdaptiveSize")
            }
        }

        item {
            if (isRowsFixed) {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 1f..8f,
                    steps = 6,
                    value = rowsFixedCount.toFloat(),
                    onValueChange = {
                        onRowsFixedChange(true)
                        onRowsFixedCountChange(it.toInt())
                    }
                )
            } else {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 30f..200f,
                    steps = 170,
                    value = rowsAdaptiveSize.value,
                    onValueChange = {
                        onRowsFixedChange(false)
                        onRowsAdaptiveSizeChange(it.toInt().dp)
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Fill",
                    selected = isRowsFill,
                    onClick = { onRowsFillChange(true) }
                )

                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Not Fill",
                    selected = !isRowsFill,
                    onClick = { onRowsFillChange(false) }
                )
            }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )
        }

        item {
            Text(text = "Columns")
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Fixed",
                    selected = isColumnsFixed,
                    onClick = { onColumnsFixedChange(true) }
                )
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Adaptive",
                    selected = !isColumnsFixed,
                    onClick = { onColumnsFixedChange(false) }
                )
            }
        }

        item {
            if (isColumnsFixed) {
                Text(text = "Fixed Cell Count: $columnsFixedCount")
            } else {
                Text(text = "Adaptive Minimum Size: $columnsAdaptiveSize")
            }
        }

        item {
            if (isColumnsFixed) {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 1f..8f,
                    steps = 6,
                    value = columnsFixedCount.toFloat(),
                    onValueChange = {
                        onColumnsFixedChange(true)
                        onColumnsFixedCountChange(it.toInt())
                    }
                )
            } else {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 30f..200f,
                    steps = 170,
                    value = columnsAdaptiveSize.value,
                    onValueChange = {
                        onColumnsFixedChange(false)
                        onColumnsAdaptiveSizeChange(it.toInt().dp)
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Fill",
                    selected = isColumnsFill,
                    onClick = { onColumnsFillChange(true) }
                )

                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Not Fill",
                    selected = !isColumnsFill,
                    onClick = { onColumnsFillChange(false) }
                )
            }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )
        }

        item {
            Text(text = "Horizontal Spacing: $horizontalSpacing")
        }

        item {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..24f,
                steps = 24,
                value = horizontalSpacing.value,
                onValueChange = {
                    onHorizontalSpacingChange(it.toInt().dp)
                }
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )
        }

        item {
            Text(text = "Vertical Spacing: $verticalSpacing")
        }

        item {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..24f,
                steps = 24,
                value = verticalSpacing.value,
                onValueChange = {
                    onVerticalSpacingChange(it.toInt().dp)
                }
            )
        }
    }
}
