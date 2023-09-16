package com.example.compose.grid.android

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.woong.compose.grid.SimpleGridCells

@Composable
fun OptionsSheet(
    itemCount: Int,
    onItemCountChange: (Int) -> Unit,
    useRandomSize: Boolean,
    onUseRandomSizeChange: (Boolean) -> Unit,
    cells: SimpleGridCells,
    onCellsChange: (SimpleGridCells) -> Unit,
    fixedCount: Int,
    onFixedCountChange: (Int) -> Unit,
    adaptiveMinSize: Dp,
    onAdaptiveMinSizeChange: (Dp) -> Unit,
    layoutDirection: LayoutDirection,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    orientation: Orientation,
    onOrientationChange: (Orientation) -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    onHorizontalArrangementChange: (Arrangement.Horizontal) -> Unit,
    verticalArrangement: Arrangement.Vertical,
    onVerticalArrangementChange: (Arrangement.Vertical) -> Unit,
    onButtonClick: () -> Unit,
) {
    val cellsOptionsState = rememberExpandableOptionState()
    val layoutDirectionOptionState = rememberExpandableOptionState()
    val orientationOptionState = rememberExpandableOptionState()
    val horizontalArrangementOptionState = rememberExpandableOptionState()
    val verticalArrangementOptionState = rememberExpandableOptionState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                SliderOption(
                    title = "Item Count",
                    indicator = itemCount.toString(),
                    value = itemCount.toFloat(),
                    valueRange = 0f..50f,
                    onValueChange = { onItemCountChange(it.toInt()) },
                    modifier = Modifier.fillMaxWidth(),
                    steps = 50,
                )
            }

            item {
                ToggleOption(
                    title = "Use Random Size Items",
                    checked = useRandomSize,
                    onCheckedChange = onUseRandomSizeChange,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item {
                ExpandableOption(
                    title = "Cell Strategy",
                    onTitleClick = {
                        if (cellsOptionsState.isExpanded) {
                            cellsOptionsState.fold()
                        } else {
                            cellsOptionsState.expand()
                        }
                    },
                    state = cellsOptionsState,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SelectableSliderOption(
                        title = "Fixed",
                        isSelected = cells is SimpleGridCells.Fixed,
                        indicator = fixedCount.toString(),
                        value = fixedCount.toFloat(),
                        valueRange = 1f..10f,
                        steps = 10,
                        onClick = { onCellsChange(SimpleGridCells.Fixed(fixedCount)) },
                        onValueChange = {
                            onFixedCountChange(it.toInt())
                            onCellsChange(SimpleGridCells.Fixed(it.toInt()))
                        },
                    )
                    SelectableSliderOption(
                        title = "Adaptive",
                        isSelected = cells is SimpleGridCells.Adaptive,
                        indicator = adaptiveMinSize.toString(),
                        value = adaptiveMinSize.value,
                        valueRange = 50f..150f,
                        steps = 19,
                        onClick = { onCellsChange(SimpleGridCells.Adaptive(adaptiveMinSize)) },
                        onValueChange = {
                            onAdaptiveMinSizeChange(it.dp)
                            onCellsChange(SimpleGridCells.Adaptive(it.dp))
                        },
                    )
                }
            }

            item {
                ExpandableOption(
                    title = "Layout Direction",
                    onTitleClick = {
                        if (layoutDirectionOptionState.isExpanded) {
                            layoutDirectionOptionState.fold()
                        } else {
                            layoutDirectionOptionState.expand()
                        }
                    },
                    state = layoutDirectionOptionState,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SelectableOption(
                        title = "Ltr",
                        isSelected = layoutDirection == LayoutDirection.Ltr,
                        onClick = { onLayoutDirectionChange(LayoutDirection.Ltr) },
                    )
                    SelectableOption(
                        title = "Rtl",
                        isSelected = layoutDirection == LayoutDirection.Rtl,
                        onClick = { onLayoutDirectionChange(LayoutDirection.Rtl) },
                    )
                }
            }

            item {
                ExpandableOption(
                    title = "Layout Orientation",
                    onTitleClick = {
                        if (orientationOptionState.isExpanded) {
                            orientationOptionState.fold()
                        } else {
                            orientationOptionState.expand()
                        }
                    },
                    state = orientationOptionState,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SelectableOption(
                        title = "Horizontal",
                        isSelected = orientation == Orientation.Horizontal,
                        onClick = { onOrientationChange(Orientation.Horizontal) },
                    )
                    SelectableOption(
                        title = "Vertical",
                        isSelected = orientation == Orientation.Vertical,
                        onClick = { onOrientationChange(Orientation.Vertical) },
                    )
                }
            }

            item {
                ExpandableOption(
                    title = "Horizontal Arrangement",
                    onTitleClick = {
                        if (horizontalArrangementOptionState.isExpanded) {
                            horizontalArrangementOptionState.fold()
                        } else {
                            horizontalArrangementOptionState.expand()
                        }
                    },
                    state = horizontalArrangementOptionState,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SelectableOption(
                        title = "Arrangement.Start",
                        isSelected = horizontalArrangement == Arrangement.Start,
                        onClick = { onHorizontalArrangementChange(Arrangement.Start) },
                    )
                    SelectableOption(
                        title = "Arrangement.Center",
                        isSelected = horizontalArrangement == Arrangement.Center,
                        onClick = { onHorizontalArrangementChange(Arrangement.Center) },
                    )
                    SelectableOption(
                        title = "Arrangement.End",
                        isSelected = horizontalArrangement == Arrangement.End,
                        onClick = { onHorizontalArrangementChange(Arrangement.End) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceAround",
                        isSelected = horizontalArrangement == Arrangement.SpaceAround,
                        onClick = { onHorizontalArrangementChange(Arrangement.SpaceAround) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceBetween",
                        isSelected = horizontalArrangement == Arrangement.SpaceBetween,
                        onClick = { onHorizontalArrangementChange(Arrangement.SpaceBetween) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceEvenly",
                        isSelected = horizontalArrangement == Arrangement.SpaceEvenly,
                        onClick = { onHorizontalArrangementChange(Arrangement.SpaceEvenly) },
                    )
                }

                ExpandableOption(
                    title = "Vertical Arrangement",
                    onTitleClick = {
                        if (verticalArrangementOptionState.isExpanded) {
                            verticalArrangementOptionState.fold()
                        } else {
                            verticalArrangementOptionState.expand()
                        }
                    },
                    state = verticalArrangementOptionState,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SelectableOption(
                        title = "Arrangement.Top",
                        isSelected = verticalArrangement == Arrangement.Top,
                        onClick = { onVerticalArrangementChange(Arrangement.Top) },
                    )
                    SelectableOption(
                        title = "Arrangement.Center",
                        isSelected = verticalArrangement == Arrangement.Center,
                        onClick = { onVerticalArrangementChange(Arrangement.Center) },
                    )
                    SelectableOption(
                        title = "Arrangement.Bottom",
                        isSelected = verticalArrangement == Arrangement.Bottom,
                        onClick = { onVerticalArrangementChange(Arrangement.Bottom) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceAround",
                        isSelected = verticalArrangement == Arrangement.SpaceAround,
                        onClick = { onVerticalArrangementChange(Arrangement.SpaceAround) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceBetween",
                        isSelected = verticalArrangement == Arrangement.SpaceBetween,
                        onClick = { onVerticalArrangementChange(Arrangement.SpaceBetween) },
                    )
                    SelectableOption(
                        title = "Arrangement.SpaceEvenly",
                        isSelected = verticalArrangement == Arrangement.SpaceEvenly,
                        onClick = { onVerticalArrangementChange(Arrangement.SpaceEvenly) },
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(16.dp)) {
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClick,
            ) {
                Text(text = "Close")
            }
        }
    }
}

@Composable
private fun SliderOption(
    title: String,
    indicator: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    steps: Int = 0,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(OptionDefaults.Height),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = indicator,
                fontWeight = FontWeight.Normal,
            )
        }

        Slider(
            modifier = Modifier.height(OptionDefaults.Height),
            value = value,
            valueRange = valueRange,
            onValueChange = onValueChange,
            steps = steps,
        )
    }
}

@Composable
private fun SelectableSliderOption(
    title: String,
    isSelected: Boolean,
    indicator: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onClick: () -> Unit,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    steps: Int = 0,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OptionDefaults.Height),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = title)
                if (isSelected) {
                    Icon(
                        painter = painterResource(R.drawable.round_check_24),
                        contentDescription = null,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OptionDefaults.Height),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = indicator,
                    fontWeight = FontWeight.Normal,
                )
                Slider(
                    modifier = Modifier.weight(0.75f),
                    value = value,
                    valueRange = valueRange,
                    onValueChange = onValueChange,
                    steps = steps,
                    enabled = isSelected,
                )
            }
        }
    }
}

@Composable
private fun SelectableOption(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(OptionDefaults.Height)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title)
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.round_check_24),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun ToggleOption(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = { onCheckedChange(!checked) },
        modifier = modifier,
    ) {
        Row(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(OptionDefaults.Height)
                    .padding(horizontal = 16.dp)
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        }
    }
}

@Composable
private fun ExpandableOption(
    title: String,
    onTitleClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ExpandableOptionState = rememberExpandableOptionState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
        Surface(
            onClick = onTitleClick,
            modifier = Modifier,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .height(OptionDefaults.Height)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                )
                Icon(
                    painter = painterResource(
                        if (state.isExpanded) {
                            R.drawable.round_keyboard_arrow_up_24
                        } else {
                            R.drawable.round_keyboard_arrow_down_24
                        }
                    ),
                    contentDescription = null,
                )
            }
        }

        AnimatedVisibility(
            visible = state.isExpanded,
            enter = expandVertically { fullHeight -> -fullHeight },
            exit = shrinkVertically { fullHeight -> -fullHeight },
            modifier = Modifier,
        ) {
            Column {
                Column(content = content)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    thickness = 1.dp,
                )
            }
        }
    }
}

@Composable
private fun rememberExpandableOptionState(
    isInitiallyExpanded: Boolean = false,
): ExpandableOptionState {
    return rememberSaveable(
        isInitiallyExpanded,
        saver = ExpandableOptionState.Saver,
    ) {
        ExpandableOptionState(
            isInitiallyExpanded = isInitiallyExpanded,
        )
    }
}

private class ExpandableOptionState(
    isInitiallyExpanded: Boolean,
) {
    var isExpanded: Boolean by mutableStateOf(isInitiallyExpanded)
        private set

    fun expand() {
        isExpanded = true
    }

    fun fold() {
        isExpanded = false
    }

    companion object {
        val Saver: Saver<ExpandableOptionState, Boolean> = Saver(
            save = { it.isExpanded },
            restore = { isExpanded ->
                ExpandableOptionState(isInitiallyExpanded = isExpanded)
            }
        )
    }
}

private object OptionDefaults {
    val Height: Dp = 56.dp
}
