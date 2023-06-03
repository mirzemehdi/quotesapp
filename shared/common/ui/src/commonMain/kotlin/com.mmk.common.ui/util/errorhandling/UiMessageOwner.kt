package com.mmk.common.ui.util.errorhandling

import com.mmk.common.ui.util.UiMessage
import kotlinx.coroutines.flow.SharedFlow

interface UiMessageOwner {
    val uiMessage: SharedFlow<UiMessage?>
}
