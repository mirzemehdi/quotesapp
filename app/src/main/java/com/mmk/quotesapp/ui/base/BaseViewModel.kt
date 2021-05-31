package com.mmk.quotesapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.quotesapp.utils.SingleEvent
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {
    protected val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    //This message object can be used to show error message in UI
    private val _message = MutableLiveData<SingleEvent<ErrorState>>()
    val message: LiveData<SingleEvent<ErrorState>> = _message


    protected fun executeUseCase(action: suspend () -> Unit) {
        _uiState.value = UiState.Loading
        viewModelScope.launch { action() }
    }


}