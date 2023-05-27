package com.mmk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.MR
import dev.icerock.moko.resources.compose.fontFamilyResource


val Typography
    @Composable
    get() = Typography(

        headlineLarge = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.tthoves.bold),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        ),

        titleLarge = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.tthoves.bold),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),

        titleMedium = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.tthoves.demibold),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.tthoves.medium),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),

        bodySmall = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.tthoves.regular),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
