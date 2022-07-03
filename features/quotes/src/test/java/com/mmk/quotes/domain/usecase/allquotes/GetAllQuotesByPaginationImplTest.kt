package com.mmk.quotes.domain.usecase.allquotes

import com.google.common.truth.Truth.assertThat
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetAllQuotesByPaginationImplTest {
    private val pageLimit = 10
    private lateinit var quotesRepository: QuotesRepository
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination

    @BeforeEach
    fun setUp() {
        quotesRepository = mockk()
        getAllQuotesByPagination = GetAllQuotesByPaginationImpl(quotesRepository)
    }

    @Test
    @DisplayName("When there are quotes,Success result with quotes list is returned")
    fun whenThereAreQuotes_SuccessResultWithActualListIsReturned() = runTest {
        val quotesList = listOf(Quote(id = "1"), Quote(id = "2"))
        coEvery {
            quotesRepository
                .getQuotesByPagination(null, pageLimit)
        } returns Result.Success(quotesList)
        val result = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        assertThat(result).isInstanceOf(Result.Success::class.java)
        result as Result.Success
        assertThat(result.data).isEqualTo(quotesList)
    }

    @Test
    @DisplayName("When there is no quote, Success result with empty list is returned")
    fun whenThereIsNoQuote_SuccessResultWithEmptyListIsReturned() = runTest {
        val quotesList = emptyList<Quote>()
        coEvery {
            quotesRepository
                .getQuotesByPagination(null, pageLimit)
        } returns Result.Success(quotesList)
        val result = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        assertThat(result).isInstanceOf(Result.Success::class.java)
        result as Result.Success
        assertThat(result.data).isEmpty()
    }

    @Test
    @DisplayName("When there is an error, Error result with its cause is returned")
    fun whenThereIsAnError_ErrorResultWithItsCauseIsReturned() = runTest {
        val errorEntity = ErrorEntity.NetworkConnection
        coEvery {
            quotesRepository
                .getQuotesByPagination(null, pageLimit)
        } returns Result.Error(errorEntity)
        val result = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        assertThat(result).isInstanceOf(Result.Error::class.java)
        result as Result.Error
        assertThat(result.errorEntity).isEqualTo(errorEntity)
    }

    @Test
    @DisplayName("When page limit is less than zero, Error result with IllegalArgumentException is returned")
    fun whenPageLimitIsLessThanZero_ErrorResultWithItsCauseIsReturned() = runTest {
        val errorEntity = ErrorEntity.Unexpected(IllegalArgumentException())
        val result = getAllQuotesByPagination.invoke(pageLimit = -1)
        assertThat(result).isInstanceOf(Result.Error::class.java)
        result as Result.Error
        assertThat(result.errorEntity).isInstanceOf(ErrorEntity.Unexpected::class.java)
        assertThat((result.errorEntity as ErrorEntity.Unexpected).e).isInstanceOf(
            IllegalArgumentException::class.java
        )
    }
}
