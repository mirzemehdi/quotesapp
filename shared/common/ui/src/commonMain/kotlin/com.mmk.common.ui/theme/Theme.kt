package com.mmk.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val LightColorPalette = MyAppColors(
    primary = Black,
    primaryDark = Black,
    onPrimary = White,
    secondary = Gold,
    onSecondary = White,
    surface = EcruWhite,
    onSurface = DarkBlack,
    background = White,
    onBackground = DarkBlack,
    inActive = Silver,
    isDark = false
)
// Night mode colors can be applied here
private val DarkColorPalette =
    MyAppColors(
        primary = Black,
        primaryDark = Black,
        onPrimary = White,
        secondary = Gold,
        onSecondary = White,
        surface = EcruWhite,
        onSurface = DarkBlack,
        background = White,
        onBackground = DarkBlack,
        inActive = Silver,
        isDark = true
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorPalette = when {
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    ProvideMyAppColors(colors = colorPalette) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}

object MyApplicationTheme {

    val colors: MyAppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Stable
class MyAppColors(
    primary: Color,
    primaryDark: Color,
    onPrimary: Color,
    secondary: Color,
    onSecondary: Color,
    surface: Color,
    onSurface: Color,
    background: Color,
    onBackground: Color,
    inActive: Color,
    isDark: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryDark by mutableStateOf(primaryDark)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var background by mutableStateOf(background)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var inActive by mutableStateOf(inActive)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: MyAppColors) {
        primary = other.primary
        primaryDark = other.primaryDark
        onPrimary = other.onPrimary
        secondary = other.secondary
        onSecondary = other.onSecondary
        surface = other.surface
        onSurface = other.onSurface
        background = other.background
        onBackground = other.onBackground
        inActive = other.inActive
        isDark = other.isDark
    }

    fun copy(): MyAppColors = MyAppColors(
        primary = primary,
        primaryDark = primaryDark,
        onPrimary = onPrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        surface = surface,
        onSurface = onSurface,
        background = background,
        onBackground = onBackground,
        inActive = inActive,
        isDark = isDark
    )
}

@Composable
fun ProvideMyAppColors(
    colors: MyAppColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors.copy() }.apply { update(colors) }
    CompositionLocalProvider(LocalColors provides colorPalette, content = content)
}

private val LocalColors = staticCompositionLocalOf<MyAppColors> {
    error("No ColorPalette provided")
}
