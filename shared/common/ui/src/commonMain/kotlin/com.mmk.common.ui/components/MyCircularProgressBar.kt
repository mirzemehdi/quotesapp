package com.mmk.common.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mmk.common.ui.theme.MyApplicationTheme

@Composable
fun MyCircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(color = MyApplicationTheme.colors.secondary, modifier = modifier)
}
