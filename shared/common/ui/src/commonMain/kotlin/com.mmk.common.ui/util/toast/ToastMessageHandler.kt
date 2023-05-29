package com.mmk.common.ui.util.toast

import com.mmk.common.ui.util.UiMessage

interface ToastMessageHandler {
    enum class Duration { LENGTH_SHORT, LENGTH_LONG }
    fun showToast(message: String?, duration: Duration = Duration.LENGTH_SHORT)
    fun showToast(uiMessage: UiMessage,duration: Duration = Duration.LENGTH_SHORT)
}