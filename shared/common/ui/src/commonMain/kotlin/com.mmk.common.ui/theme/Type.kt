package com.mmk.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.MR
import dev.icerock.moko.resources.compose.fontFamilyResource

private val baseFont = MR.fonts.notosans

@Composable
fun getTypography() = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamilyResource(baseFont.bold),
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),

    titleLarge = TextStyle(
        fontFamily = fontFamilyResource(baseFont.bold),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    titleMedium = TextStyle(
        fontFamily = fontFamilyResource(baseFont.semibold),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = fontFamilyResource(baseFont.regular),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    bodySmall = TextStyle(
        fontFamily = fontFamilyResource(baseFont.regular),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
