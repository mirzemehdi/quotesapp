package com.mmk.domain.interaction.quote.addquote

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository

class AddNewQuoteUseCaseImpl constructor(private val repository: QuotesRepository) :
    AddNewQuoteUseCase {
    override suspend fun invoke(quote: Quote): Result<Unit> {
        return repository.addNewQuote(quote)
    }
}