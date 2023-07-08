package com.mmk.common.ui.util.toast

import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.extensions.showToast
import dev.icerock.moko.resources.desc.desc
import platform.UIKit.UIApplication

class IosToastMessageHandler : ToastMessageHandler {
    override fun showToast(message: String?, duration: ToastMessageHandler.Duration) {
        message?.let {
            val application = UIApplication.sharedApplication
            application.showToast(message, duration)
        }
    }

    override fun showToast(uiMessage: UiMessage, duration: ToastMessageHandler.Duration) {
        val messageText = when (uiMessage) {
            is UiMessage.Message -> uiMessage.message ?: ""
            is UiMessage.Resource -> uiMessage.id.desc().localized()
        }
        showToast(messageText)
    }
}
