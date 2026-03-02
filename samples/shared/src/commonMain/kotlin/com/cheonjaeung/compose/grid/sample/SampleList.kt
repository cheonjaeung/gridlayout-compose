package com.cheonjaeung.compose.grid.sample

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleList() {
    MaterialTheme {
        var currentSampleScreen: SampleListItemData? by remember { mutableStateOf(null) }

        PlatformBackHandler(enabled = currentSampleScreen != null) {
            currentSampleScreen = null
        }

        Crossfade(targetState = currentSampleScreen) { screen ->
            if (screen == null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            title = {
                                Text(text = "Grid Samples")
                            }
                        )
                    }
                ) { inset ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(inset)
                    ) {
                        items(
                            items = listOf(
                                SampleListItemData("Horizontal/Vertical Grid") {
                                    HorizontalVerticalGridSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Box Grid") {
                                    BoxGridSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Track Grid Cells") {
                                    TrackSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Responsive Grid Cells") {
                                    ResponsiveSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Lazy Grid Track Cells") {
                                    LazyGridTrackSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Lazy Grid Responsive Cells") {
                                    LazyGridResponsiveSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Lazy Staggered Grid Track Cells") {
                                    LazyStaggeredGridTrackSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                                SampleListItemData("Lazy Staggered Grid Responsive Cells") {
                                    LazyStaggeredGridResponsiveSample(onBack = {
                                        currentSampleScreen = null
                                    })
                                },
                            )
                        ) { item ->
                            SampleListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .padding(horizontal = 16.dp),
                                onClick = { currentSampleScreen = item },
                                text = item.text
                            )
                        }
                    }
                }
            } else {
                screen.content()
            }
        }
    }
}

@Composable
private fun SampleListItem(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
    }
}

private data class SampleListItemData(
    val text: String,
    val content: @Composable () -> Unit
)
