import androidx.compose.runtime.Composable
import com.chrynan.navigation.ExperimentalNavigationApi
import com.chrynan.navigation.Navigator
import com.chrynan.navigation.SingleNavigationContext
import com.mmk.root.RootScreen
import com.mmk.root.TopLevelDestination

@OptIn(ExperimentalNavigationApi::class)
@Composable
fun MainView(
    darkTheme: Boolean = false,
    navigator: Navigator<TopLevelDestination, SingleNavigationContext<TopLevelDestination>>
) = RootScreen(darkTheme, navigator)
