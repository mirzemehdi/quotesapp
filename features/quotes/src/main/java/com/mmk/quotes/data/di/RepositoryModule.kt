package com.mmk.quotes.data.di

import com.mmk.quotes.data.mappers.QuoteMapper
import com.mmk.quotes.data.repository.QuotesRepositoryImpl
import com.mmk.quotes.domain.repository.QuotesRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    factory { QuoteMapper() }
    factory<QuotesRepository> { QuotesRepositoryImpl(get(), get()) }
}
