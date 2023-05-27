package com.mmk.common.ui.components

import androidx.compose.runtime.Composable
import com.mmk.common.ui.util.errorhandling.UiMessageOwner
import com.mmk.common.ui.util.extensions.showAsToastMessage

@Composable
fun UiMessageOwnerComponent(
    uiMessageOwner: UiMessageOwner,
    content: @Composable () -> Unit
) {
    uiMessageOwner.uiMessage.showAsToastMessage()
    content()
}
