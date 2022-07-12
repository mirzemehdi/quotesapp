package com.mmk.quotes.domain.usecase.addquote

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote

interface AddNewQuote {
    suspend operator fun invoke(quote: Quote): Result<Unit>
}
