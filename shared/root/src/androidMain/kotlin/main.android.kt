import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.chrynan.navigation.ExperimentalNavigationApi
import com.chrynan.navigation.compose.rememberNavigator
import com.chrynan.navigation.goBack
import com.mmk.root.TopLevelDestination
import com.mmk.root.components.RootScreen

@OptIn(ExperimentalNavigationApi::class)
@Composable
fun MainView(darkTheme: Boolean = false) {
    val navigator = rememberNavigator(initialDestination = TopLevelDestination.QUOTES)
    BackHandler { navigator.goBack() }
    RootScreen(darkTheme, navigator)
}
