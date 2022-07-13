package com.mmk.common.ui

import androidx.annotation.StringRes

sealed class UiState {
    object HasData : UiState()
    object Loading : UiState()
    object NoData : UiState()
    data class Error(val errorMessage: ErrorMessage? = null) : UiState()
}

sealed class ErrorMessage {
    data class ResourceId(@StringRes val id: Int) : ErrorMessage()
    data class Message(val message: String?) : ErrorMessage()
}