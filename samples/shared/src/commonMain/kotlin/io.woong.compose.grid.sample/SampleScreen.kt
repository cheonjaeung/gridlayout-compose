package io.woong.compose.grid.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SampleScreen() {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "samples")
        }
    }
}
