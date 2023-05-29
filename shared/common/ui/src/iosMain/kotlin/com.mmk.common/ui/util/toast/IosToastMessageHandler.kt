package com.mmk.common.ui.util.toast

import com.mmk.common.ui.util.UiMessage

class IosToastMessageHandler : ToastMessageHandler {
    override fun showToast(message: String?, duration: ToastMessageHandler.Duration) {
        message?.let {
            TODO("Not implemented yet")
        }
    }

    override fun showToast(uiMessage: UiMessage, duration: ToastMessageHandler.Duration) {
        TODO("Not implemented yet")
        val messageText = when (uiMessage) {
            is UiMessage.Message -> uiMessage.message ?: ""
            is UiMessage.Resource -> ""
        }
    }
}