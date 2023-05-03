package com.mmk.quotes.presentation.addnewquote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mmk.quotes.presentation.addnewquote.AddNewQuoteScreen

const val addNewQuoteRoute = "route_add_new_quote"

fun NavController.navigateToAddNewQuote(navOptions: NavOptions? = null) {
    navigate(addNewQuoteRoute, navOptions = navOptions)
}

fun NavGraphBuilder.addNewQuoteScreen(onBackClick: () -> Unit) {
    composable(route = addNewQuoteRoute) {
        AddNewQuoteScreen(onBackPress = onBackClick)
    }
}
