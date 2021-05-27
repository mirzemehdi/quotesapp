package com.mmk.data.di

import com.mmk.data.repository.quotes.FakeQuotesRepositoryImpl
import com.mmk.data.repository.quotes.QuotesPagingSource
import com.mmk.data.repository.quotes.QuotesRepositoryImpl
import com.mmk.domain.repository.QuotesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule= module {
    factory<QuotesRepository> { QuotesRepositoryImpl(get(named(QUALIFIER_QUOTES))) }
}