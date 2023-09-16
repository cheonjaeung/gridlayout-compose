package com.example.compose.grid.desktop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.zIndex

@Composable
fun OptionPane(
    itemCount: Int,
    onItemCountChange: (Int) -> Unit,
    useRandomSize: Boolean,
    onUseRandomSizeChange: (Boolean) -> Unit,
    layoutDirection: LayoutDirection,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    orientation: Orientation,
    onOrientationChange: (Orientation) -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    onHorizontalArrangementChange: (Arrangement.Horizontal) -> Unit,
    verticalArrangement: Arrangement.Vertical,
    onVerticalArrangementChange: (Arrangement.Vertical) -> Unit,
    modifier: Modifier = Modifier,
) {
    val orientationOptionState = rememberExpandableOptionState()
    val layoutDirectionOptionState = rememberExpandableOptionState()
    val horizontalArrangementOptionState = rememberExpandableOptionState()
    val verticalArrangementOptionState = rememberExpandableOptionState()

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(5f),
                )
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(4f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(3f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(2f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
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

@OptIn(ExperimentalMaterial3Api::class)
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
                    painter = painterResource("check_black_24dp.svg"),
                    contentDescription = null,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToggleOption(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = { onCheckedChange(!checked) },
        modifier = modifier.zIndex(1f),
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

@OptIn(ExperimentalMaterial3Api::class)
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
            modifier = Modifier.zIndex(1f),
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
                            "keyboard_arrow_up_black_24dp.svg"
                        } else {
                            "keyboard_arrow_down_black_24dp.svg"
                        }
                    ),
                    contentDescription = null,
                )
            }
        }

        AnimatedVisibility(
            visible = state.isExpanded,
            enter = slideInVertically { fullHeight -> -fullHeight },
            exit = slideOutVertically { fullHeight -> -fullHeight },
            modifier = Modifier.zIndex(-1f),
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
