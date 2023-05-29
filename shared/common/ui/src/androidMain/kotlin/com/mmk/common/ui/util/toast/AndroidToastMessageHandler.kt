package com.mmk.common.ui.util.toast

import android.content.Context
import android.widget.Toast
import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.extensions.showToast

class AndroidToastMessageHandler(private val context: Context) : ToastMessageHandler {
    override fun showToast(message: String?, duration: ToastMessageHandler.Duration) {
        val toastDuration = when (duration) {
            ToastMessageHandler.Duration.LENGTH_SHORT -> Toast.LENGTH_SHORT
            ToastMessageHandler.Duration.LENGTH_LONG -> Toast.LENGTH_LONG
        }
        message?.let { context.showToast(text = it, duration = toastDuration) }
    }

    override fun showToast(uiMessage: UiMessage, duration: ToastMessageHandler.Duration) {
        val messageString = when (uiMessage) {
            is UiMessage.Message -> uiMessage.message ?: ""
            is UiMessage.Resource -> context.getString(uiMessage.id.resourceId)
        }
        showToast(messageString, duration)
    }
}