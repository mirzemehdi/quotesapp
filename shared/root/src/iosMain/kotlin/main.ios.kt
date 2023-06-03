
import androidx.compose.ui.window.ComposeUIViewController
import com.mmk.root.RootScreen
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    return ComposeUIViewController { RootScreen() }
}
