
import androidx.compose.ui.window.ComposeUIViewController
import com.mmk.root.components.RootScreen
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    return ComposeUIViewController { RootScreen() }
}
