package com.mmk.quotesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mmk.profile.presentation.navigation.navigateToProfile
import com.mmk.quotes.presentation.addnewquote.navigation.navigateToAddNewQuote
import com.mmk.quotes.presentation.allquotes.navigation.allQuotesRoute
import com.mmk.quotes.presentation.allquotes.navigation.navigateToAllQuotes
import com.mmk.quotesapp.navigation.MainNavigation
import com.mmk.root.RootScreen
import com.mmk.root.TopLevelDestination

@Composable
fun MainScreen(darkTheme: Boolean = false) {
    val navController = rememberNavController()
    val currentNavEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        navController.currentBackStackEntry
    )
    val currentRoute = currentNavEntry?.destination?.route ?: allQuotesRoute

    RootScreen(
        darkTheme = darkTheme,
        currentRoute = currentRoute,
        navigationContent = { MainNavigation(navController = navController) },
        onSelectedTopLevelDestination = { topLevelDestination ->
            val navOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                TopLevelDestination.QUOTES -> navController.navigateToAllQuotes(navOptions)
                TopLevelDestination.ADD_QUOTE -> navController.navigateToAddNewQuote(
                    navOptions
                )
                TopLevelDestination.PROFILE -> navController.navigateToProfile(navOptions)
            }
        }
    )
}
