package com.cheonjaeung.compose.grid.sample.option

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OptionScaffold(
    onBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            OptionScaffoldTopBar(
                modifier = Modifier.fillMaxWidth(),
                onBack = onBack,
                title = title,
            )
        },
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OptionScaffoldTopBar(
    onBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}
