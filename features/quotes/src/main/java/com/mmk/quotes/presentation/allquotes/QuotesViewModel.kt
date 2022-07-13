package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmk.common.ui.UiState

class QuotesViewModel : ViewModel() {

    private val _getAllQuotesUiState: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val getAllQuotesUiState: LiveData<UiState> = _getAllQuotesUiState
}
