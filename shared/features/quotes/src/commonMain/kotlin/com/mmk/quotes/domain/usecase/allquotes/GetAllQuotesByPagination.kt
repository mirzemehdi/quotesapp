package com.mmk.quotes.domain.usecase.allquotes

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface GetAllQuotesByPagination {
    suspend operator fun invoke(pageIndex: String? = null, pageLimit: Int): Result<List<Quote>>
    fun observeFirstPageQuotes(pageLimit: Int): Flow<Result<List<Quote>>>
}
