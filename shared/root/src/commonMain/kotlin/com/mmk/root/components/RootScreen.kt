package com.mmk.root.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.chrynan.navigation.*
import com.chrynan.navigation.compose.collectAsState
import com.chrynan.navigation.compose.rememberNavigator
import com.mmk.common.ui.MR
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.common.ui.theme.getColors
import com.mmk.root.TopLevelDestination
import dev.icerock.moko.resources.compose.painterResource

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

    MyApplicationTheme(darkTheme) {
        Surface(modifier = Modifier.fillMaxSize(), color = getColors().background) {
            RootScreen(
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
    }
}

@Composable
private fun RootScreen(
    currentRoute: String,
    onSelectedTopLevelDestination: (TopLevelDestination) -> Unit,
    navigationContent: @Composable () -> Unit
) {

    val isBottomNavVisible = currentRoute != TopLevelDestination.ADD_QUOTE.route
    val selectedBottomNavItem = TopLevelDestination.getDestinationFromRoute(currentRoute)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                navigationContent()
            }
            AnimatedVisibility(isBottomNavVisible, modifier = Modifier.fillMaxWidth()) {
                BottomNavigationView(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDestination = selectedBottomNavItem
                ) {
                    onSelectedTopLevelDestination(it)
                }
            }
        }
        AddQuoteButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = isBottomNavVisible,
            onClick = { onSelectedTopLevelDestination(TopLevelDestination.ADD_QUOTE) }
        )
    }
}

@Composable
private fun AddQuoteButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    val addButtonSize = 74.dp
    AnimatedVisibility(
        isVisible,
        modifier = modifier
            .size(addButtonSize)
            .offset(y = (addButtonSize / 2 - 56.dp))
            .zIndex(2f)
    ) {
        FloatingActionButton(
            containerColor = getColors().secondary,
            contentColor = getColors().onSecondary,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
            shape = CircleShape,
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(MR.images.ic_add),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
