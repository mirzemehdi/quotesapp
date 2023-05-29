package com.mmk.quotes.presentation.di

import com.mmk.core.util.multiPlatformViewModel
import com.mmk.quotes.presentation.addnewquote.AddNewQuoteVM
import com.mmk.quotes.presentation.allquotes.QuotesVM
import org.koin.dsl.module

internal val viewModelModule = module {
    multiPlatformViewModel { QuotesVM(get()) }
    multiPlatformViewModel { AddNewQuoteVM(get()) }
}
