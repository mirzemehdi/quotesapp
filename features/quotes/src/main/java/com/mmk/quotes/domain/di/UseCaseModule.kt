package com.mmk.quotes.domain.di

import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPaginationImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetAllQuotesByPagination> { GetAllQuotesByPaginationImpl(get()) }
}
