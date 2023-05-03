package com.mmk.quotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mmk.profile.presentation.navigation.profileScreen
import com.mmk.quotes.presentation.addnewquote.navigation.addNewQuoteScreen
import com.mmk.quotes.presentation.allquotes.navigation.allQuotesRoute
import com.mmk.quotes.presentation.allquotes.navigation.allQuotesScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = allQuotesRoute) {
        allQuotesScreen()
        addNewQuoteScreen(onBackClick = navController::popBackStack)
        profileScreen()
    }
}
