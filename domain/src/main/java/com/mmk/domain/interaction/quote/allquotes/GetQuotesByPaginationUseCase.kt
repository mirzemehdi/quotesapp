package com.mmk.domain.interaction.quote.allquotes

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface GetQuotesByPaginationUseCase {
    suspend operator fun invoke(pageIndex: String?, pageLimit: Int): Result<List<Quote>>

}

