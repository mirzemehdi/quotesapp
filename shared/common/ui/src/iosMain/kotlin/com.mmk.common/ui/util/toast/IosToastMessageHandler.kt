package com.mmk.common.ui.util.toast

import com.mmk.common.ui.util.UiMessage

// TODO implement toast message in ios
class IosToastMessageHandler : ToastMessageHandler {
    override fun showToast(message: String?, duration: ToastMessageHandler.Duration) {
        message?.let {
            println("ios toast message: $message")
        }
    }

    override fun showToast(uiMessage: UiMessage, duration: ToastMessageHandler.Duration) {
//        val messageText = when (uiMessage) {
//            is UiMessage.Message -> uiMessage.message ?: ""
//            is UiMessage.Resource -> ""
//        }
    }
}
