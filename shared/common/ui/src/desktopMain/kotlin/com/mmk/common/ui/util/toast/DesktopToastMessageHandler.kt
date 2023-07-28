package com.mmk.common.ui.util.toast

import com.mmk.common.ui.util.UiMessage
import dev.icerock.moko.resources.desc.desc
import javax.swing.JOptionPane

class DesktopToastMessageHandler : ToastMessageHandler {
    override fun showToast(message: String?, duration: ToastMessageHandler.Duration) {
        message?.let {
            JOptionPane.showMessageDialog(null, message, message, JOptionPane.INFORMATION_MESSAGE)
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
