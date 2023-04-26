package com.mmk.quotesapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.profile.presentation.navigation.navigateToProfile
import com.mmk.quotes.presentation.addnewquote.navigation.addNewQuoteRoute
import com.mmk.quotes.presentation.addnewquote.navigation.navigateToAddNewQuote
import com.mmk.quotes.presentation.allquotes.navigation.navigateToAllQuotes
import com.mmk.quotesapp.R
import com.mmk.quotesapp.navigation.MainNavigation
import com.mmk.quotesapp.navigation.TopLevelDestination

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    MainScreen(
        navHostController = navController,
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
                TopLevelDestination.ADD_QUOTE -> navController.navigateToAddNewQuote(navOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfile(navOptions)
            }
        }
    )
}

@Composable
private fun MainScreen(
    navHostController: NavHostController,
    onSelectedTopLevelDestination: (TopLevelDestination) -> Unit
) {
    val currentRoute by navHostController.currentBackStackEntryFlow
        .collectAsStateWithLifecycle(navHostController.currentBackStackEntry)
    val isBottomNavVisible = currentRoute?.destination?.route != addNewQuoteRoute

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                MainNavigation(navController = navHostController)
            }
            AnimatedVisibility(isBottomNavVisible, modifier = Modifier.fillMaxWidth()) {
                BottomNavigationView(modifier = Modifier.fillMaxWidth()) {
                    onSelectedTopLevelDestination(it)
                }
            }
        }
        val addButtonSize = 74.dp
        AnimatedVisibility(
            isBottomNavVisible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(addButtonSize)
                .offset(y = (addButtonSize / 2 - 56.dp))
                .zIndex(2f)
        ) {
            FloatingActionButton(
                containerColor = MyApplicationTheme.colors.secondary,
                contentColor = MyApplicationTheme.colors.onSecondary,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                shape = CircleShape,
                onClick = { onSelectedTopLevelDestination(TopLevelDestination.ADD_QUOTE) },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationView(
    modifier: Modifier = Modifier,
    onSelectedTopLevelDestination: (TopLevelDestination) -> Unit
) {
    BottomNavigation(
        backgroundColor = MyApplicationTheme.colors.primary,
        modifier = modifier
    ) {

        var selectedDestination by remember { mutableStateOf(TopLevelDestination.QUOTES) }
        val disabledItems = listOf(TopLevelDestination.ADD_QUOTE)

        TopLevelDestination.values().forEachIndexed { index, destination ->
            BottomNavigationItem(
                label = stringResource(id = destination.labelResId),
                icon = destination.iconResId?.let { painterResource(id = it) },
                selected = selectedDestination == destination,
                enabled = destination !in disabledItems
            ) {
                selectedDestination = destination
                onSelectedTopLevelDestination(destination)
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    label: String,
    icon: Painter? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        selected = selected,
        enabled = enabled,
        onClick = { onClick() },
        icon = {
            icon?.let {
                Icon(
                    painter = icon,
                    contentDescription = label,
                    tint = if (selected) MyApplicationTheme.colors.onPrimary else MyApplicationTheme.colors.inActive,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .size(24.dp)
                )
            }
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = if (selected) MyApplicationTheme.colors.onPrimary else MyApplicationTheme
                    .colors.inActive,
                modifier = Modifier.padding(8.dp)
            )
        },
        selectedContentColor = MyApplicationTheme.colors.onPrimary,
        unselectedContentColor = MyApplicationTheme.colors.inActive

    )
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MyApplicationTheme {
        val navHostController = rememberNavController()
        MainScreen(navHostController, {})
    }
}
