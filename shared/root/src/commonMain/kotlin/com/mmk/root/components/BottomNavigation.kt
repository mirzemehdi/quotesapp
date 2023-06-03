package com.mmk.root.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.theme.getColors
import com.mmk.root.TopLevelDestination
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun BottomNavigationView(
    modifier: Modifier = Modifier,
    selectedDestination: TopLevelDestination = TopLevelDestination.QUOTES,
    onSelectedTopLevelDestination: (TopLevelDestination) -> Unit
) {
    BottomNavigation(
        backgroundColor = getColors().primary,
        modifier = modifier
    ) {

        val disabledItems = listOf(TopLevelDestination.ADD_QUOTE)

        TopLevelDestination.values().forEachIndexed { index, destination ->
            BottomNavigationItem(
                label = stringResource(destination.labelStringResource),
                icon = destination.iconRes?.let { painterResource(it) },
                selected = selectedDestination == destination,
                enabled = destination !in disabledItems
            ) {
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
                    tint = if (selected) getColors().onPrimary else getColors().inActive,
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
                color = if (selected) getColors().onPrimary else getColors().inActive,
                modifier = Modifier.padding(8.dp)
            )
        },
        selectedContentColor = getColors().onPrimary,
        unselectedContentColor = getColors().inActive

    )
}
