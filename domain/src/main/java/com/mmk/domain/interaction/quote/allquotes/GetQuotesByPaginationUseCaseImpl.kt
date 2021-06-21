package com.mmk.domain.interaction.quote.allquotes

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository


class GetQuotesByPaginationUseCaseImpl constructor(private val repository: QuotesRepository) :
    GetQuotesByPaginationUseCase {

    override suspend fun invoke(pageIndex: String?, pageLimit: Int): Result<List<Quote>> {
        return repository.getQuotesByPagination(pageIndex, pageLimit)

    }


}

