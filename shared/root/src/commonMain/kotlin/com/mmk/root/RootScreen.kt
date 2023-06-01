package com.mmk.root

import androidx.compose.runtime.Composable
import com.chrynan.navigation.*
import com.chrynan.navigation.compose.collectAsState
import com.chrynan.navigation.compose.rememberNavigator

@OptIn(ExperimentalNavigationApi::class)
@Composable
internal fun RootScreen(darkTheme: Boolean = false) {
    val navigator = rememberNavigator(initialDestination = TopLevelDestination.QUOTES)
    RootScreen(darkTheme = darkTheme, navigator = navigator)
}

@OptIn(ExperimentalNavigationApi::class)
@Composable
internal fun RootScreen(
    darkTheme: Boolean = false,
    navigator: Navigator<TopLevelDestination, SingleNavigationContext<TopLevelDestination>>
) {
    val currentRoute = navigator.store.destination.collectAsState().value.route
    MainScreen(
        darkTheme = darkTheme,
        currentRoute = currentRoute,
        navigationContent = { MainNavigation(navigator = navigator) },
        onSelectedTopLevelDestination = { topLevelDestination ->

            when (topLevelDestination) {
                TopLevelDestination.QUOTES -> navigator.goToSingleTop(TopLevelDestination.QUOTES)
                TopLevelDestination.ADD_QUOTE -> {
                    navigator.goToSingleTop(TopLevelDestination.ADD_QUOTE)
                }
                TopLevelDestination.PROFILE -> navigator.goToSingleTop(TopLevelDestination.PROFILE)
            }
        }
    )
}
