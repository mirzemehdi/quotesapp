package com.mmk.quotes.data.repository

import androidx.paging.PagingSource
import com.google.common.truth.Truth
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.util.PagingException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class QuotesPagingSourceTest {

    private lateinit var quotesPagingSource: QuotesPagingSource
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination

    @BeforeEach
    fun setUp() {
        getAllQuotesByPagination = mockk()
        quotesPagingSource = QuotesPagingSource(getAllQuotesByPagination)
    }

    @Test
    fun `given success list when load then paging has exact data`() = runTest {
        val pageLimit = 2
        val pageIndex = null
        val quoteList = listOf(Quote("1"), Quote("2"))
        coEvery { getAllQuotesByPagination(pageIndex, pageLimit) } returns Result.success(quoteList)

        val expected = PagingSource.LoadResult.Page(
            data = quoteList, prevKey = null, nextKey = quoteList.last().id
        )

        val actual = quotesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = pageIndex,
                loadSize = pageLimit,
                placeholdersEnabled = false
            )
        )

        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given error when load then LoadResult Error with PagingException is returned`() = runTest {
        val pageLimit = 2
        val pageIndex = null
        val errorEntity = ErrorEntity.networkConnection()
        coEvery { getAllQuotesByPagination(pageIndex, pageLimit) } returns Result.error(errorEntity)

        val expected = PagingSource.LoadResult.Error<String, Quote>(
            PagingException(errorEntity)
        )

        val actual = quotesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = pageIndex,
                loadSize = pageLimit,
                placeholdersEnabled = false
            )
        )

        Truth.assertThat(actual).isEqualTo(expected)
    }
}
