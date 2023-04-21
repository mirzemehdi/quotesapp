package com.mmk.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.R

private val tthovesFontFamily = FontFamily(
    Font(R.font.tthoves_regular, weight = FontWeight.Normal),
    Font(R.font.tthoves_black, weight = FontWeight.Black),
    Font(R.font.tthoves_bold, weight = FontWeight.Bold),
    Font(R.font.tthoves_demibold, weight = FontWeight.SemiBold),
    Font(R.font.tthoves_medium, weight = FontWeight.Medium),
)

val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = tthovesFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),

    titleLarge = TextStyle(
        fontFamily = tthovesFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    titleMedium = TextStyle(
        fontFamily = tthovesFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

)
