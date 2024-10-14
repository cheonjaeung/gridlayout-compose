package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheonjaeung.compose.grid.sample.option.ArrangementOptionDialog
import com.cheonjaeung.compose.grid.sample.option.Dialogs
import com.cheonjaeung.compose.grid.sample.option.LayoutDirectionOptionDialog
import com.cheonjaeung.compose.grid.sample.option.LayoutOrientationOptionDialog
import com.cheonjaeung.compose.grid.HorizontalGrid
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SampleScreen() {
    MaterialTheme {
        var dialog: Dialogs? by remember { mutableStateOf(null) }

        var colors: PersistentList<Color> by remember {
            mutableStateOf(
                persistentListOf(
                    randomColor(),
                    randomColor(),
                    randomColor(),
                    randomColor()
                )
            )
        }

        val systemLayoutDirection = LocalLayoutDirection.current
        var layoutDirection by remember { mutableStateOf(systemLayoutDirection) }
        var isVerticalGrid by remember { mutableStateOf(true) }
        var cells: SimpleGridCells by remember { mutableStateOf(SimpleGridCells.Fixed(3)) }
        var horizontalArrangement by remember { mutableStateOf(Arrangement.Start) }
        var verticalArrangement by remember { mutableStateOf(Arrangement.Top) }

        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scrollState = rememberScrollState()

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            ModalNavigationDrawer(
                drawerContent = {
                    SampleDrawerSheet(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(),
                    ) { lazyListScope ->
                        with(lazyListScope) {
                            title("Layout Options")

                            menu(
                                onClick = { dialog = Dialogs.LayoutDirection },
                                text = "Layout Direction",
                            )

                            menu(
                                onClick = { dialog = Dialogs.LayoutOrientation },
                                text = "Layout Orientation",
                            )

                            menu(
                                onClick = { dialog = Dialogs.Arrangement },
                                text = "Arrangement",
                            )
                        }
                    }
                },
                drawerState = drawerState,
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SampleTopBar(
                            modifier = Modifier.fillMaxWidth(),
                            onMenuClick = {
                                coroutineScope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            },
                        )
                    },
                    bottomBar = {
                        SampleBottomBar(
                            modifier = Modifier.fillMaxWidth(),
                            onResetClick = {
                                colors = persistentListOf()
                                layoutDirection = systemLayoutDirection
                                isVerticalGrid = true
                                cells = SimpleGridCells.Fixed(3)
                                horizontalArrangement = Arrangement.Start
                                verticalArrangement = Arrangement.Top
                            },
                            onFloatingActionButtonClick = {
                                colors = colors.mutate {
                                    it.add(randomColor())
                                }
                            },
                        )
                    },
                    content = { innerPadding ->
                        SampleContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            colors = colors,
                            isVerticalGrid = isVerticalGrid,
                            cells = cells,
                            horizontalArrangement = horizontalArrangement,
                            verticalArrangement = verticalArrangement,
                            scrollState = scrollState,
                        )
                    }
                )
            }

            when (dialog) {
                Dialogs.LayoutDirection -> {
                    LayoutDirectionOptionDialog(
                        onDismissRequest = { dialog = null },
                        layoutDirection = layoutDirection,
                        onLayoutDirectionChange = { layoutDirection = it }
                    )
                }

                Dialogs.LayoutOrientation -> {
                    LayoutOrientationOptionDialog(
                        onDismissRequest = { dialog = null },
                        layoutOrientation = if (isVerticalGrid) {
                            Orientation.Vertical
                        } else {
                            Orientation.Horizontal
                        },
                        onLayoutOrientationChange = { isVerticalGrid = it == Orientation.Vertical }
                    )
                }

                Dialogs.Arrangement -> {
                    ArrangementOptionDialog(
                        onDismissRequest = { dialog = null },
                        horizontalArrangement = horizontalArrangement,
                        onHorizontalArrangementChange = { horizontalArrangement = it },
                        verticalArrangement = verticalArrangement,
                        onVerticalArrangementChange = { verticalArrangement = it },
                    )
                }

                else -> Unit
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleTopBar(
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Compose Grid Sample")
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                )
            }
        }
    )
}

@Composable
private fun SampleBottomBar(
    onResetClick: () -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onResetClick) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Clear"
                )
            }
        },
        modifier = modifier,
        floatingActionButton = {
            SmallFloatingActionButton(onClick = onFloatingActionButtonClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                )
            }
        }
    )
}

@Composable
private fun SampleContent(
    colors: ImmutableList<Color>,
    isVerticalGrid: Boolean,
    cells: SimpleGridCells,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
) {
    if (isVerticalGrid) {
        VerticalGrid(
            columns = cells,
            modifier = modifier.verticalScroll(scrollState),
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
        ) {
            for ((index, color) in colors.withIndex()) {
                SampleItem(
                    modifier = Modifier.aspectRatio(1f),
                    color = color,
                    text = (index + 1).toString(),
                )
            }
        }
    } else {
        HorizontalGrid(
            rows = cells,
            modifier = modifier.horizontalScroll(scrollState),
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
        ) {
            for ((index, color) in colors.withIndex()) {
                SampleItem(
                    modifier = Modifier.aspectRatio(1f),
                    color = color,
                    text = (index + 1).toString(),
                )
            }
        }
    }
}

@Composable
private fun SampleItem(
    color: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(4.dp)

    Box(
        modifier = modifier.then(
            Modifier
                .clip(shape)
                .border(width = 2.dp, color = color.copy(alpha = 0.75f), shape = shape)
                .background(color = color)
        )
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

private fun randomColor(): Color {
    val randomRed = Random.nextInt(150, 220)
    val randomGreen = Random.nextInt(150, 220)
    val randomBlue = Random.nextInt(150, 220)
    return Color(red = randomRed, green = randomGreen, blue = randomBlue, alpha = 127)
}
