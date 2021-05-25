package com.mmk.quotesapp.ui.base

import androidx.annotation.StringRes

/**
 * Created by mirzemehdi on 8/13/20
 */

sealed class UiState {
    object HasData : UiState()
    object Loading : UiState()
    object NoData : UiState()
    data class Error(val errorState: ErrorState?) : UiState()
}

sealed class ErrorState() {
    data class ResourceId(@StringRes val id: Int) : ErrorState()
    data class Message(val message: String?) : ErrorState()
}


