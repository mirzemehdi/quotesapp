package com.mmk.root

import androidx.compose.runtime.Composable
import com.chrynan.navigation.*
import com.chrynan.navigation.compose.NavigationContainer
import com.mmk.profile.presentation.ProfileScreen
import com.mmk.quotes.presentation.addnewquote.AddNewQuoteRoute
import com.mmk.quotes.presentation.allquotes.QuotesRoute

@OptIn(ExperimentalNavigationApi::class)
@Composable
internal fun MainNavigation(navigator: Navigator<TopLevelDestination, SingleNavigationContext<TopLevelDestination>>) {
    NavigationContainer(navigator = navigator) { destination ->
        when (destination) {
            TopLevelDestination.QUOTES -> QuotesRoute()
            TopLevelDestination.ADD_QUOTE -> AddNewQuoteRoute(
                onBackPress = { navigator.goBack() }
            )
            TopLevelDestination.PROFILE -> ProfileScreen()
        }
    }
}

@OptIn(ExperimentalNavigationApi::class)
internal fun Navigator<TopLevelDestination, SingleNavigationContext<TopLevelDestination>>.goToSingleTop(
    destination: TopLevelDestination
) {
    reset()
    val currentDestination = this.store.destination.current
    if (currentDestination != destination) {
        goTo(destination)
    }
}
