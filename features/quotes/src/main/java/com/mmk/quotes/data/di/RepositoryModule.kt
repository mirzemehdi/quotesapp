package com.mmk.quotes.data.di

import androidx.paging.PagingSource
import com.mmk.quotes.data.mappers.QuoteMapper
import com.mmk.quotes.data.repository.QuotesPagingSource
import com.mmk.quotes.data.repository.QuotesRepositoryImpl
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import org.koin.dsl.module

private typealias QuotesPagingSourceFactory = () -> PagingSource<String, Quote>

val repositoryModule = module {
    factory { QuoteMapper() }
    factory<QuotesRepository> { QuotesRepositoryImpl(get(), get()) }
    factory<PagingSource<String, Quote>> { QuotesPagingSource(get()) }
    factory<QuotesPagingSourceFactory> { { get() } }
}
