package com.mmk.domain.interaction.quote.addquote

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface AddNewQuoteUseCase {
    suspend operator fun invoke(quote: Quote): Result<Unit>
}