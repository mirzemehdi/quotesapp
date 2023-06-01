import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.mmk.common.ui.di.uiModule
import com.mmk.core.di.coreModule
import com.mmk.profile.profileFeatureModule
import com.mmk.quotes.quotesFeatureModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

@Composable
internal fun TestView() {
    Text("Hellooooo")
}
fun MainViewController(): UIViewController {

    return ComposeUIViewController { TestView() }
}

object Initalizer {
    private var initalized: Boolean = false
    fun init() {
        if (!initalized) {
            startKoin {
                val mod = listOf(coreModule) + listOf(uiModule) + profileFeatureModule + quotesFeatureModule
                modules(mod)
            }
            initalized = true
        }
    }
}
