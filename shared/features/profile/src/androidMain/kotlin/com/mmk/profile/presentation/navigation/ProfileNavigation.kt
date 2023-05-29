package com.mmk.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mmk.profile.presentation.ProfileScreen

// TODO make navigation multiplatform
const val profileRoute = "route_profile"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(profileRoute, navOptions = navOptions)
}

fun NavGraphBuilder.profileScreen() {
    composable(route = profileRoute) {
        ProfileScreen()
    }
}
