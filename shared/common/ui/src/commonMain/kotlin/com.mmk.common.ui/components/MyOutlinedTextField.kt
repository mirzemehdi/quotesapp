package com.mmk.common.ui.components

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.common.ui.util.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOutlinedTextField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    label: String = "",
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE
) {

    val customTextSelectionColors = TextSelectionColors(
        handleColor = MyApplicationTheme.colors.secondary,
        backgroundColor = MyApplicationTheme.colors.secondary.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        OutlinedTextField(
            value = textFieldState.value,
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MyApplicationTheme.colors.primary,
                cursorColor = MyApplicationTheme.colors.secondary,
                focusedBorderColor = MyApplicationTheme.colors.secondary,
                focusedLabelColor = MyApplicationTheme.colors.secondary
            ),
            onValueChange = { textFieldState.onValueChanged(it) },
            singleLine = singleLine,
            maxLines = maxLines,
            label = { Text(text = label) },
            modifier = modifier
        )
    }
}
