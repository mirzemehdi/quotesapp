package com.mmk.domain.di

import com.mmk.domain.interaction.quote.addquote.AddNewQuoteUseCase
import com.mmk.domain.interaction.quote.addquote.AddNewQuoteUseCaseImpl
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetQuotesByPaginationUseCase> { GetQuotesByPaginationUseCaseImpl(get()) }
    factory<AddNewQuoteUseCase> { AddNewQuoteUseCaseImpl(get()) }
}