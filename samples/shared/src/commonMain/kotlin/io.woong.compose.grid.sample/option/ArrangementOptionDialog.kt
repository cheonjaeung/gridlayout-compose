package io.woong.compose.grid.sample.option

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid
import kotlinx.collections.immutable.persistentListOf

private val HorizontalArrangementTypes = persistentListOf(
    ArrangementOption(Arrangement.Start, "Start"),
    ArrangementOption(Arrangement.Center, "Center"),
    ArrangementOption(Arrangement.End, "End"),
    ArrangementOption(Arrangement.SpaceAround, "Space Around"),
    ArrangementOption(Arrangement.SpaceBetween, "Space Between"),
    ArrangementOption(Arrangement.SpaceEvenly, "Space Evenly"),
    ArrangementOption(Arrangement.spacedBy(16.dp), "Spaced By 16dp")
)

private val VerticalArrangementTypes = persistentListOf(
    ArrangementOption(Arrangement.Top, "Top"),
    ArrangementOption(Arrangement.Center, "Center"),
    ArrangementOption(Arrangement.Bottom, "Bottom"),
    ArrangementOption(Arrangement.SpaceAround, "Space Around"),
    ArrangementOption(Arrangement.SpaceBetween, "Space Between"),
    ArrangementOption(Arrangement.SpaceEvenly, "Space Evenly"),
    ArrangementOption(Arrangement.spacedBy(16.dp), "Spaced By 16dp")
)

private data class ArrangementOption<A>(
    val arrangement: A,
    val displayName: String,
)

@Composable
fun ArrangementOptionDialog(
    onDismissRequest: () -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    onHorizontalArrangementChange: (Arrangement.Horizontal) -> Unit,
    verticalArrangement: Arrangement.Vertical,
    onVerticalArrangementChange: (Arrangement.Vertical) -> Unit,
) {
    OptionDialog(
        onDismissRequest = onDismissRequest,
        title = "Arrangement"
    ) {
        Header(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = "Horizontal",
        )

        VerticalGrid(
            columns = SimpleGridCells.Adaptive(100.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            for (option in HorizontalArrangementTypes) {
                SelectableButton(
                    modifier = Modifier.height(56.dp),
                    text = option.displayName,
                    onClick = { onHorizontalArrangementChange(option.arrangement) },
                    selected = horizontalArrangement == option.arrangement
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            thickness = 1.dp,
        )

        Header(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = "Vertical",
        )

        VerticalGrid(
            columns = SimpleGridCells.Adaptive(100.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            for (option in VerticalArrangementTypes) {
                SelectableButton(
                    modifier = Modifier.height(56.dp),
                    text = option.displayName,
                    onClick = { onVerticalArrangementChange(option.arrangement) },
                    selected = verticalArrangement == option.arrangement
                )
            }
        }
    }
}

@Composable
private fun Header(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
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
        Text(
            text = text,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}
