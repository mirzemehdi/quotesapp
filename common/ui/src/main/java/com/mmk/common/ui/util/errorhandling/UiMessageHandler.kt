package com.mmk.common.ui.util.errorhandling

import com.mmk.common.ui.util.UiMessage
import kotlinx.coroutines.flow.SharedFlow

interface UiMessageHandler {
    val uiMessage: SharedFlow<UiMessage?>
    suspend fun sendMessage(uiMessage: UiMessage)
}
