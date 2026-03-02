package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.ExperimentalGridApi
import com.cheonjaeung.compose.grid.ExtendedGridCells
import com.cheonjaeung.compose.grid.GridTrack
import kotlin.random.Random

private data class LazyStaggeredGridTrackItemData(
    val color: androidx.compose.ui.graphics.Color,
    val height: Dp
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGridApi::class)
@Composable
fun LazyStaggeredGridTrackSample(onBack: () -> Unit) {
    var gridItemCount by remember { mutableStateOf(24) }
    val items by remember(gridItemCount) {
        derivedStateOf {
            List(gridItemCount) {
                LazyStaggeredGridTrackItemData(
                    color = randomColor(),
                    height = Random.nextInt(50, 150).dp
                )
            }
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Lazy Staggered Grid Track") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = "Items: $gridItemCount")
                Slider(
                    value = gridItemCount.toFloat(),
                    onValueChange = { gridItemCount = it.toInt() },
                    valueRange = 1f..1000f
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Tracks: Fixed(80.dp), Weight(1f), Weight(2f)")
            }
        }
    ) { inset ->
        LazyVerticalStaggeredGrid(
            columns = ExtendedGridCells.StaggeredGridCells.Track(
                GridTrack.Fixed(80.dp),
                GridTrack.Weight(1f),
                GridTrack.Weight(2f),
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(inset)
        ) {
            itemsIndexed(items) { index, item ->
                SampleItem(
                    modifier = Modifier.fillMaxWidth().height(item.height).padding(4.dp),
                    color = item.color,
                    text = index.toString()
                )
            }
        }
    }
}
