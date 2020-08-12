package com.mmk.quotesapp.utils

/**
 * Created by mirzemehdi on 8/13/20
 */
sealed class UiState {
    object Loading:UiState()
    object NotLoading:UiState()
    object Error:UiState()
}