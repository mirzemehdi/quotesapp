package com.mmk.quotes.domain.usecase.allquotes

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetAllQuotesByPaginationImpl(private val quotesRepository: QuotesRepository) :
    GetAllQuotesByPagination {
    override suspend fun invoke(pageIndex: String?, pageLimit: Int): Result<List<Quote>> {
        if (pageLimit < 0) return Result.Error(
            ErrorEntity.Unexpected(
                IllegalArgumentException("Page limit can not less than zero")
            )
        )
        return quotesRepository.getQuotesByPagination(pageIndex, pageLimit)
    }

    override fun observeFirstPageQuotes(pageLimit: Int): Flow<Result<List<Quote>>> {
        if (pageLimit < 0) return flowOf(
            Result.Error(
                ErrorEntity.Unexpected(
                    IllegalArgumentException("Page limit can not less than zero")
                )
            )
        )
        return quotesRepository.listenForDataChange(pageLimit)
    }
}
