import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@Composable
internal fun TestView() {
    Text("Hellooooo")
}
fun MainViewController(): UIViewController {

    return ComposeUIViewController { TestView() }
}
