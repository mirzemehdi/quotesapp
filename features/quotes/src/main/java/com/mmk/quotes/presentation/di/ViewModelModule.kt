package com.mmk.quotes.presentation.di

import com.mmk.quotes.presentation.addnewquote.AddNewQuoteVM
import com.mmk.quotes.presentation.allquotes.QuotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { QuotesViewModel(get()) }
    viewModel { AddNewQuoteVM(get()) }
}
