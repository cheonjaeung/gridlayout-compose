import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.woong.compose.grid.ExperimentalGridLayoutApi
import io.woong.compose.grid.VerticalGrid

@OptIn(ExperimentalGridLayoutApi::class)
fun main() = application {
    val colors = arrayOf(
        Color.Red, Color.Green, Color.Blue,
        Color.Cyan, Color.Magenta, Color.Yellow,
        Color.White, Color.Gray, Color.Black,
        Color.Red, Color.Green, Color.Blue,
        Color.Cyan, Color.Magenta, Color.Yellow,
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Grid Sample",
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = DpSize(1200.dp, 800.dp)
        )
    ) {
        VerticalGrid(
            columnCount = 3,
            modifier = Modifier.border(width = 2.dp, color = Color.Red),
        ) {
            ColorBox(
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = colors[1]),
                color = colors[0],
            )
            ColorBox(
                modifier = Modifier
                    .size(110.dp)
                    .border(width = 1.dp, color = colors[2]),
                color = colors[1],
            )
            ColorBox(
                modifier = Modifier
                    .size(120.dp)
                    .border(width = 1.dp, color = colors[3]),
                color = colors[2],
            )

            ColorBox(
                modifier = Modifier
                    .size(120.dp)
                    .border(width = 1.dp, color = colors[4]),
                color = colors[3],
            )
            ColorBox(
                modifier = Modifier
                    .size(110.dp)
                    .border(width = 1.dp, color = colors[5]),
                color = colors[4],
            )
            ColorBox(
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = colors[6]),
                color = colors[5],
            )

            ColorBox(
                modifier = Modifier
                    .size(80.dp)
                    .border(width = 1.dp, color = colors[7]),
                color = colors[6],
            )
            ColorBox(
                modifier = Modifier
                    .size(90.dp)
                    .border(width = 1.dp, color = colors[8]),
                color = colors[7],
            )
            ColorBox(
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = colors[9]),
                color = colors[8],
            )

            ColorBox(
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = colors[10]),
                color = colors[9],
            )
            ColorBox(
                modifier = Modifier
                    .size(90.dp)
                    .border(width = 1.dp, color = colors[11]),
                color = colors[10],
            )
            ColorBox(
                modifier = Modifier
                    .size(80.dp)
                    .border(width = 1.dp, color = colors[0]),
                color = colors[11],
            )
        }
    }
}

@Composable
private fun ColorBox(
    color: Color,
    modifier: Modifier = Modifier,
) {
    val coloredModifier = modifier.then(
        Modifier.background(color = color)
    )
    Box(modifier = coloredModifier)
}
