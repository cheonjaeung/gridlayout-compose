import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.woong.compose.grid.VerticalGrid

fun main() = application {
    val colors = arrayOf(
        Color.Red, Color.Green, Color.Blue,
        Color.Cyan, Color.Magenta, Color.Yellow,
        Color.White, Color.Gray, Color.Black,
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Grid Sample",
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = DpSize(800.dp, 600.dp)
        )
    ) {
        VerticalGrid(
            columnCount = 3,
            modifier = Modifier.fillMaxSize(),
        ) {
            for ((i, color) in colors.withIndex()) {
                ColorBox(
                    modifier = Modifier.size(100.dp),
                    text = i.toString(),
                    color = color,
                )
            }
        }
    }
}

@Composable
private fun ColorBox(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val coloredModifier = modifier.then(
        Modifier.background(color = color)
    )
    Box(modifier = coloredModifier) {
        BasicText(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = Color.White),
            text = text,
        )
    }
}
