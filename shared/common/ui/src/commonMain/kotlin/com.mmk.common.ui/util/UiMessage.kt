package com.mmk.common.ui.util

import dev.icerock.moko.resources.StringResource

sealed interface UiMessage {

    data class Resource(val id: StringResource) : UiMessage
    data class Message(val message: String?) : UiMessage
}
