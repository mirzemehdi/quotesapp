package com.mmk.domain.interaction.quote.allquotes

import androidx.paging.PagingData
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow


class GetQuotesByPaginationUseCaseImpl constructor(private val repository: QuotesRepository) :
    GetQuotesByPaginationUseCase {

    override suspend fun invoke(): Result<Flow<PagingData<Quote>>> {
        return repository.getQuotesByPagination()

    }
}

