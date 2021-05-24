package com.mmk.data.repository

import com.mmk.data.repository.base.BaseRepository
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository
import org.koin.core.component.KoinApiExtension


class QuotesRepositoryImpl:QuotesRepository,BaseRepository() {

    override suspend fun getQuotesByPagination(
        pageNumber: Int,
        pageLimit: Int
    ): Result<List<Quote>> {

        fetchInBackground(networkDataProvider = {testFunction()})
    }



    suspend fun testFunction():Result<String>{
        return Result.Success("dsf")
    }

}