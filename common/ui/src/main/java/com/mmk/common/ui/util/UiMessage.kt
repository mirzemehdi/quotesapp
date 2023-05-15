package com.mmk.common.ui.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiMessage {

    data class ResourceId(@StringRes val id: Int) : UiMessage
    data class Message(val message: String?) : UiMessage

    fun getMessage(context: Context): String {
        return when (this) {
            is Message -> this.message ?: ""
            is ResourceId -> context.resources.getString(this.id)
        }
    }

    // Can be used directly in Jetpack Compose
    @Composable
    fun getMessage(): String {
        return when (this) {
            is Message -> this.message ?: ""
            is ResourceId -> stringResource(id = this.id)
        }
    }
}
