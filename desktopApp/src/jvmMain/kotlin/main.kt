
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.mmk.root.AppInitializer

fun main() = application {
    AppInitializer.initialize{}
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(),
        title = "QuotesApp"
    ) {
        MainView()
    }
}