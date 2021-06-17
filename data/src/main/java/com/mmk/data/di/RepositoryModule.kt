package com.mmk.data.di

import com.mmk.data.repository.quotes.QuotesRepositoryImpl
import com.mmk.domain.repository.QuotesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<QuotesRepository> { QuotesRepositoryImpl(get()) }

}