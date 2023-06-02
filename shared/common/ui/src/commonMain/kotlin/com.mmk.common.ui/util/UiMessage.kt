package com.mmk.common.ui.util

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

sealed interface UiMessage {

    data class Resource(val id: StringResource) : UiMessage
    data class Message(val message: String?) : UiMessage
}

@Composable
fun UiMessage.getMessage(): String {
    return when (this) {
        is UiMessage.Message -> this.message ?: ""
        is UiMessage.Resource -> stringResource(this.id)
    }
}
