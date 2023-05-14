package com.mmk.common.ui.components

import androidx.compose.runtime.Composable
import com.mmk.common.ui.util.errorhandling.UiMessageHandler
import com.mmk.common.ui.util.extensions.showAsToastMessage

@Composable
fun UiMessageHandlerComponent(
    uiMessageHandler: UiMessageHandler,
    content: @Composable () -> Unit
) {
    uiMessageHandler.uiMessage.showAsToastMessage()
    content()
}
