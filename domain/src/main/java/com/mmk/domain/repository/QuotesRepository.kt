package com.mmk.domain.repository

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface QuotesRepository {

    suspend fun getQuotesByPagination(pageNumber: Int, pageLimit: Int): Result<List<Quote>>
}