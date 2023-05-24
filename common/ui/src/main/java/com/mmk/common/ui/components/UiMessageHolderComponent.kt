package com.mmk.common.ui.components

import androidx.compose.runtime.Composable
import com.mmk.common.ui.util.errorhandling.UiMessageHandler
import com.mmk.common.ui.util.extensions.showAsToastMessage

@Composable
fun UiMessageHolderComponent(
    uiMessageHolder: UiMessageHandler,
    content: @Composable () -> Unit
) {
    uiMessageHolder.uiMessage.showAsToastMessage()
    content()
}
