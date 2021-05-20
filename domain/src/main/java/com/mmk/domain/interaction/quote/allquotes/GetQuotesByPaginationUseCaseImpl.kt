package com.mmk.domain.interaction.quote.allquotes

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository


class GetQuotesByPaginationUseCaseImpl constructor(private val repository: QuotesRepository) :
    GetQuotesByPaginationUseCase {
    override suspend fun invoke(pageNumber: Int, pageLimit: Int): Result<List<Quote>> =
        repository.getQuotesByPagination(pageNumber = pageNumber, pageLimit = pageLimit)
}

