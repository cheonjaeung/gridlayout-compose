package io.woong.compose.grid.sample.option

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LayoutOrientationOptionDialog(
    onDismissRequest: () -> Unit,
    layoutOrientation: Orientation,
    onLayoutOrientationChange: (Orientation) -> Unit,
) {
    OptionDialog(
        onDismissRequest = onDismissRequest,
        title = "Layout Orientation"
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SelectableButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                onClick = { onLayoutOrientationChange(Orientation.Horizontal) },
                text = "Horizontal",
                selected = layoutOrientation == Orientation.Horizontal,
            )

            SelectableButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                onClick = { onLayoutOrientationChange(Orientation.Vertical) },
                text = "Vertical",
                selected = layoutOrientation == Orientation.Vertical,
            )
        }
    }
}

@Composable
private fun SelectableButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        border = if (selected) {
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            null
        },
    ) {
        Text(text = text)
    }
}
