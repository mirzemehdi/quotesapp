
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 400.dp, height = 300.dp),
        title = "QuotesApp"
    ) {
        Text("Hello World")

    }
}