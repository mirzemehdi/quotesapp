package com.mmk.quotesapp.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.mmk.quotesapp.R
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.model.PictureEntity
import com.mmk.quotesapp.ui.fragments.quoteslist.QuotesAdapter

/**
 * Created by mirzemehdi on 8/13/20
 */
sealed class UiState {
    object Loading:UiState()
    object NotLoading:UiState()
    object Error:UiState()
}



