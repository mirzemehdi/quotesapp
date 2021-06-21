package com.mmk.data.repository.quotes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.model.onSuccess
import timber.log.Timber
import java.io.IOException

class QuotesPagingSource(private val getQuotesByPaginationUseCase: GetQuotesByPaginationUseCase) :
    PagingSource<String, Quote>() {

    override fun getRefreshKey(state: PagingState<String, Quote>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Quote> {
        return try {

            val quotesListResponse = getQuotesByPaginationUseCase(params.key, params.loadSize)
            quotesListResponse.onSuccess {
                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isNullOrEmpty()) null else it.last().id
                )
            }
            LoadResult.Error(Throwable((quotesListResponse as? Result.Error)?.message))

        } catch (exception: IOException) {
            Timber.e(exception)
            LoadResult.Error(exception)

        } catch (exception: Exception) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }
}