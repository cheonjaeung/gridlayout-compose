package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BoxGridSample(onBack: () -> Unit) {
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SampleTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Box Grid",
                onBack = onBack
            )
        },
        sheetContent = {

        }
    ) { inset ->

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
