package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheonjaeung.compose.grid.HorizontalGrid
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import kotlin.random.Random

@Composable
fun SampleList() {
    MaterialTheme {
        val initialLayoutDirection = LocalLayoutDirection.current
        var layoutDirection by remember { mutableStateOf(initialLayoutDirection) }
        var isDropDownMenuExpanded by remember { mutableStateOf(false) }

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
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
            var horizontalArrangementSpacing by remember { mutableStateOf(8.dp) }
            var verticalArrangement by remember { mutableStateOf(Arrangement.Top) }
            var verticalArrangementSpacing by remember { mutableStateOf(8.dp) }

            SampleScaffold(
                modifier = Modifier.fillMaxSize(),
                title = "Grid Sample",
                actions = {
                    IconButton(onClick = { isDropDownMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )

                        DropdownMenu(
                            expanded = isDropDownMenuExpanded,
                            onDismissRequest = { isDropDownMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = when (layoutDirection) {
                                            LayoutDirection.Ltr -> "Set to RTL"
                                            LayoutDirection.Rtl -> "Set to LTR"
                                        }
                                    )
                                },
                                onClick = {
                                    layoutDirection = when (layoutDirection) {
                                        LayoutDirection.Ltr -> LayoutDirection.Rtl
                                        LayoutDirection.Rtl -> LayoutDirection.Ltr
                                    }
                                    isDropDownMenuExpanded = false
                                }
                            )
                        }
                    }
                },
                content = { inset ->
                    when (orientation) {
                        Orientation.Vertical -> {
                            VerticalGrid(
                                modifier = Modifier
                                    .padding(inset)
                                    .fillMaxSize(),
                                columns = cells,
                                horizontalArrangement = horizontalArrangement,
                                verticalArrangement = verticalArrangement
                            ) {
                                gridItems.forEachIndexed { index, color ->
                                    SampleItem(
                                        modifier = if (fill) {
                                            Modifier.aspectRatio(1f)
                                        } else {
                                            Modifier.padding(4.dp)
                                        },
                                        color = color,
                                        text = index.toString()
                                    )
                                }
                            }
                        }

                        Orientation.Horizontal -> {
                            HorizontalGrid(
                                modifier = Modifier
                                    .padding(inset)
                                    .fillMaxSize(),
                                rows = cells,
                                horizontalArrangement = horizontalArrangement,
                                verticalArrangement = verticalArrangement
                            ) {
                                gridItems.forEachIndexed { index, color ->
                                    SampleItem(
                                        modifier = if (fill) {
                                            Modifier.aspectRatio(1f)
                                        } else {
                                            Modifier.padding(4.dp)
                                        },
                                        color = color,
                                        text = index.toString()
                                    )
                                }
                            }
                        }
                    }
                },
                sheetContent = {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
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
                                    onClick = { orientation = Orientation.Horizontal }
                                )
                                RadioButtonWithText(
                                    modifier = Modifier.weight(1f),
                                    text = "Vertical",
                                    selected = orientation == Orientation.Vertical,
                                    onClick = { orientation = Orientation.Vertical }
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
                                modifier = Modifier.weight(1f),
                                valueRange = 0f..12f,
                                steps = 11,
                                value = gridItemCount.toFloat(),
                                onValueChange = { gridItemCount = it.toInt() }
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
                                    selected = cells is SimpleGridCells.Fixed,
                                    onClick = { isFixed = true }
                                )
                                RadioButtonWithText(
                                    modifier = Modifier.weight(1f),
                                    text = "Adaptive",
                                    selected = cells is SimpleGridCells.Adaptive,
                                    onClick = { isFixed = false }
                                )
                            }
                        }

                        item {
                            when (cells) {
                                is SimpleGridCells.Fixed -> {
                                    Text(text = "Fixed Cell Count: $fixedCount")
                                }

                                is SimpleGridCells.Adaptive -> {
                                    Text(text = "Adaptive Minimum Size: $adaptiveSize")
                                }
                            }
                        }

                        item {
                            when (cells) {
                                is SimpleGridCells.Fixed -> {
                                    Slider(
                                        modifier = Modifier.weight(1f),
                                        valueRange = 0f..9f,
                                        steps = 8,
                                        value = fixedCount.toFloat(),
                                        onValueChange = {
                                            isFixed = true
                                            fixedCount = it.toInt()
                                        }
                                    )
                                }

                                is SimpleGridCells.Adaptive -> {
                                    Slider(
                                        modifier = Modifier.weight(1f),
                                        valueRange = 30f..200f,
                                        steps = 170,
                                        value = adaptiveSize.value,
                                        onValueChange = {
                                            isFixed = false
                                            adaptiveSize = it.toInt().dp
                                        }
                                    )
                                }
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
                                    selected = cells.fillCellSize(),
                                    onClick = { fill = true }
                                )

                                RadioButtonWithText(
                                    modifier = Modifier.weight(1f),
                                    text = "Not Fill",
                                    selected = !cells.fillCellSize(),
                                    onClick = { fill = false }
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
                                onClick = { horizontalArrangement = item }
                            )
                        }

                        item {
                            RadioButtonWithText(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Arrangement\$SpacedBy ($horizontalArrangementSpacing)",
                                selected = horizontalArrangement !in horizontalArrangementOptions,
                                onClick = {
                                    horizontalArrangement = Arrangement.spacedBy(horizontalArrangementSpacing)
                                }
                            )
                        }

                        item {
                            Slider(
                                modifier = Modifier.fillMaxWidth(),
                                valueRange = 0f..24f,
                                steps = 24,
                                value = horizontalArrangementSpacing.value,
                                onValueChange = {
                                    horizontalArrangementSpacing = it.toInt().dp
                                    horizontalArrangement = Arrangement.spacedBy(horizontalArrangementSpacing)
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
                                onClick = { verticalArrangement = item }
                            )
                        }

                        item {
                            RadioButtonWithText(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Arrangement\$SpacedBy ($verticalArrangementSpacing)",
                                selected = verticalArrangement !in verticalArrangementOptions,
                                onClick = {
                                    verticalArrangement = Arrangement.spacedBy(verticalArrangementSpacing)
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
                                        verticalArrangement = Arrangement.spacedBy(verticalArrangementSpacing)
                                    }
                                )

                                Text(text = "Arrangement\$SpacedBy ($verticalArrangementSpacing)")
                            }
                        }

                        item {
                            Slider(
                                modifier = Modifier.fillMaxWidth(),
                                valueRange = 0f..24f,
                                steps = 24,
                                value = verticalArrangementSpacing.value,
                                onValueChange = {
                                    verticalArrangementSpacing = it.toInt().dp
                                    verticalArrangement = Arrangement.spacedBy(verticalArrangementSpacing)
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleScaffold(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = title)
                },
                actions = actions
            )
        },
        sheetContent = sheetContent,
        content = content
    )
}

@Composable
private fun SampleItem(
    color: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(4.dp)

    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 2.dp, color = color.copy(alpha = 0.75f), shape = shape)
            .background(color = color)
            .then(modifier)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
private fun RadioButtonWithText(
    onClick: () -> Unit,
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(text = text)
    }
}

private fun randomColor(): Color {
    val randomRed = Random.nextInt(150, 220)
    val randomGreen = Random.nextInt(150, 220)
    val randomBlue = Random.nextInt(150, 220)
    return Color(red = randomRed, green = randomGreen, blue = randomBlue, alpha = 127)
}
