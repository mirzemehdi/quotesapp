package com.mmk.quotesapp.di

import com.mmk.quotesapp.ui.fragments.quoteslist.QuotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { QuotesViewModel(get()) }
}

