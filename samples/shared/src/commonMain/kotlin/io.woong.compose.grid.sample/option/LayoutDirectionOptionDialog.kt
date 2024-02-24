package io.woong.compose.grid.sample.option

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun LayoutDirectionOptionDialog(
    onDismissRequest: () -> Unit,
    layoutDirection: LayoutDirection,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
) {
    OptionDialog(
        onDismissRequest = onDismissRequest,
        title = "Layout Direction"
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SelectableButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    onClick = { onLayoutDirectionChange(LayoutDirection.Ltr) },
                    text = "LTR",
                    selected = layoutDirection == LayoutDirection.Ltr,
                )

                SelectableButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    onClick = { onLayoutDirectionChange(LayoutDirection.Rtl) },
                    text = "RTL",
                    selected = layoutDirection == LayoutDirection.Rtl,
                )
            }
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
