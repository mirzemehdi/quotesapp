package com.mmk.quotes.domain.usecase.addquote

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository

class AddNewQuoteImpl(private val quotesRepository: QuotesRepository) : AddNewQuote {
    override suspend fun invoke(quote: Quote): Result<Unit> {

        return quotesRepository.addNewQuote(quote)
    }
}
