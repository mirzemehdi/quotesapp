package com.mmk.quotes.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mmk.core.model.Result
import com.mmk.quotes.data.util.PagingException
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination

data class QuotesPagingSource(private val getAllQuotesByPagination: GetAllQuotesByPagination) :
    PagingSource<String, Quote>() {
    companion object {
        private val INITIAL_KEY = null
    }

    override fun getRefreshKey(state: PagingState<String, Quote>): String? {
        return INITIAL_KEY
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Quote> {
        val startPageIndex = params.key ?: INITIAL_KEY
        val result = getAllQuotesByPagination.invoke(startPageIndex, params.loadSize)
        return when (result) {
            is Result.Error -> LoadResult.Error(PagingException(result.errorEntity))
            is Result.Success -> LoadResult.Page(
                data = result.data,
                prevKey = null,
                nextKey = if (result.data.isNullOrEmpty()) null else result.data.last().id
            )
        }
    }
}
