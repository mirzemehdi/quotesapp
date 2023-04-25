package com.mmk.quotes.presentation.allquotes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mmk.quotes.presentation.allquotes.QuotesScreen

const val allQuotesRoute = "route_all_quotes"

fun NavController.navigateToAllQuotes(navOptions: NavOptions? = null) {
    navigate(allQuotesRoute, navOptions = navOptions)
}

fun NavGraphBuilder.allQuotesScreen() {
    composable(route = allQuotesRoute) {
        QuotesScreen()
    }
}
