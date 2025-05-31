package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.HorizontalGrid
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalVerticalGridSample(onBack: () -> Unit) {
    var orientation by remember { mutableStateOf(Orientation.Vertical) }

    var isFixed by remember { mutableStateOf(true) }
    var fixedCount by remember { mutableStateOf(5) }
    var adaptiveSize by remember { mutableStateOf(100.dp) }
    var fill by remember { mutableStateOf(true) }
    val cells by remember(isFixed, fixedCount, adaptiveSize, fill) {
        derivedStateOf {
            if (isFixed) {
                SimpleGridCells.Fixed(fixedCount, fill)
            } else {
                SimpleGridCells.Adaptive(adaptiveSize, fill)
            }
        }
    }

    var gridItemCount by remember { mutableStateOf(8) }
    val gridItems by remember(gridItemCount) {
        derivedStateOf {
            List(gridItemCount) { randomColor() }
        }
    }

    var horizontalArrangement by remember { mutableStateOf(Arrangement.Start) }
    var horizontalSpacing by remember { mutableStateOf(8.dp) }
    var verticalArrangement by remember { mutableStateOf(Arrangement.Top) }
    var verticalSpacing by remember { mutableStateOf(8.dp) }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SampleTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Horizontal/Vertical Grid",
                onBack = onBack
            )
        },
        sheetContent = {
            SheetContent(
                orientation = orientation,
                onOrientationChange = { orientation = it },
                isFixed = isFixed,
                onFixedChange = { isFixed = it },
                fixedCount = fixedCount,
                onFixedCountChange = { fixedCount = it },
                adaptiveSize = adaptiveSize,
                onAdaptiveSizeChange = { adaptiveSize = it },
                fill = fill,
                onFillChange = { fill = it },
                gridItemCount = gridItemCount,
                onGridItemCountChange = { gridItemCount = it },
                horizontalArrangement = horizontalArrangement,
                onHorizontalArrangementChange = { horizontalArrangement = it },
                verticalArrangement = verticalArrangement,
                onVerticalArrangementChange = { verticalArrangement = it },
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
            orientation = orientation,
            cells = cells,
            items = gridItems,
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement
        )
    }
}

@Composable
private fun SampleContent(
    orientation: Orientation,
    cells: SimpleGridCells,
    items: List<Color>,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier
) {
    when (orientation) {
        Orientation.Vertical -> {
            VerticalGrid(
                modifier = modifier,
                columns = cells,
                horizontalArrangement = horizontalArrangement,
                verticalArrangement = verticalArrangement
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

        Orientation.Horizontal -> {
            HorizontalGrid(
                modifier = modifier,
                rows = cells,
                horizontalArrangement = horizontalArrangement,
                verticalArrangement = verticalArrangement
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

@Composable
private fun SheetContent(
    orientation: Orientation,
    onOrientationChange: (Orientation) -> Unit,
    isFixed: Boolean,
    onFixedChange: (Boolean) -> Unit,
    fixedCount: Int,
    onFixedCountChange: (Int) -> Unit,
    adaptiveSize: Dp,
    onAdaptiveSizeChange: (Dp) -> Unit,
    fill: Boolean,
    onFillChange: (Boolean) -> Unit,
    gridItemCount: Int,
    onGridItemCountChange: (Int) -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    onHorizontalArrangementChange: (Arrangement.Horizontal) -> Unit,
    verticalArrangement: Arrangement.Vertical,
    onVerticalArrangementChange: (Arrangement.Vertical) -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Horizontal",
                    selected = orientation == Orientation.Horizontal,
                    onClick = { onOrientationChange(Orientation.Horizontal) }
                )
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Vertical",
                    selected = orientation == Orientation.Vertical,
                    onClick = { onOrientationChange(Orientation.Vertical) }
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Fixed",
                    selected = isFixed,
                    onClick = { onFixedChange(true) }
                )
                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Adaptive",
                    selected = !isFixed,
                    onClick = { onFixedChange(false) }
                )
            }
        }

        item {
            if (isFixed) {
                Text(text = "Fixed Cell Count: $fixedCount")
            } else {
                Text(text = "Adaptive Minimum Size: $adaptiveSize")
            }
        }

        item {
            if (isFixed) {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 1f..8f,
                    steps = 6,
                    value = fixedCount.toFloat(),
                    onValueChange = {
                        onFixedChange(true)
                        onFixedCountChange(it.toInt())
                    }
                )
            } else {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 30f..200f,
                    steps = 170,
                    value = adaptiveSize.value,
                    onValueChange = {
                        onFixedChange(false)
                        onAdaptiveSizeChange(it.toInt().dp)
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
                    selected = fill,
                    onClick = { onFillChange(true) }
                )

                RadioButtonWithText(
                    modifier = Modifier.weight(1f),
                    text = "Not Fill",
                    selected = !fill,
                    onClick = { onFillChange(false) }
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
            Text(text = "Horizontal Arrangement")
        }

        val horizontalArrangementOptions = listOf(
            Arrangement.Start,
            Arrangement.Center,
            Arrangement.End,
            Arrangement.SpaceAround,
            Arrangement.SpaceEvenly,
            Arrangement.SpaceBetween
        )
        items(items = horizontalArrangementOptions) { item ->
            RadioButtonWithText(
                modifier = Modifier.fillMaxWidth(),
                text = "$item",
                selected = horizontalArrangement == item,
                onClick = { onHorizontalArrangementChange(item) }
            )
        }

        item {
            RadioButtonWithText(
                modifier = Modifier.fillMaxWidth(),
                text = "Arrangement\$SpacedBy ($horizontalSpacing)",
                selected = horizontalArrangement !in horizontalArrangementOptions,
                onClick = {
                    onHorizontalArrangementChange(Arrangement.spacedBy(horizontalSpacing))
                }
            )
        }

        item {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..24f,
                steps = 24,
                value = horizontalSpacing.value,
                onValueChange = {
                    onHorizontalSpacingChange(it.toInt().dp)
                    onHorizontalArrangementChange(Arrangement.spacedBy(horizontalSpacing))
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
            Text(text = "Vertical Arrangement")
        }

        val verticalArrangementOptions = listOf(
            Arrangement.Top,
            Arrangement.Center,
            Arrangement.Bottom,
            Arrangement.SpaceAround,
            Arrangement.SpaceEvenly,
            Arrangement.SpaceBetween
        )
        items(items = verticalArrangementOptions) { item ->
            RadioButtonWithText(
                modifier = Modifier.fillMaxWidth(),
                text = "$item",
                selected = verticalArrangement == item,
                onClick = { onVerticalArrangementChange(item) }
            )
        }

        item {
            RadioButtonWithText(
                modifier = Modifier.fillMaxWidth(),
                text = "Arrangement\$SpacedBy ($verticalSpacing)",
                selected = verticalArrangement !in verticalArrangementOptions,
                onClick = {
                    onVerticalArrangementChange(Arrangement.spacedBy(verticalSpacing))
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = verticalArrangement !in verticalArrangementOptions,
                    onClick = {
                        onVerticalArrangementChange(Arrangement.spacedBy(verticalSpacing))
                    }
                )

                Text(text = "Arrangement\$SpacedBy ($verticalSpacing)")
            }
        }

        item {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..24f,
                steps = 24,
                value = verticalSpacing.value,
                onValueChange = {
                    onVerticalSpacingChange(it.toInt().dp)
                    onVerticalArrangementChange(Arrangement.spacedBy(verticalSpacing))
                }
            )
        }
    }
}
