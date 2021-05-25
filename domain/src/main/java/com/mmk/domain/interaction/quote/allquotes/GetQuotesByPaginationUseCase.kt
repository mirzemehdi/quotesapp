package com.mmk.domain.interaction.quote.allquotes

import androidx.paging.PagingData
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface GetQuotesByPaginationUseCase {
    suspend operator fun invoke(): Result<Flow<PagingData<Quote>>>
}

