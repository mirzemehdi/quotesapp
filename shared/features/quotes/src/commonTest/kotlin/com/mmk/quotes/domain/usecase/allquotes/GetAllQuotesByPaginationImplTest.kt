package com.mmk.quotes.domain.usecase.allquotes

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.data.repository.FakeQuotesRepository
import com.mmk.quotes.domain.model.Quote
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class GetAllQuotesByPaginationImplTest {
    private val pageLimit = 10
    private lateinit var quotesRepository: FakeQuotesRepository
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination

    @BeforeTest
    fun setUp() {
        quotesRepository = FakeQuotesRepository()
        getAllQuotesByPagination = GetAllQuotesByPaginationImpl(quotesRepository)
    }

    @Test
    fun whenThereAreQuotes_SuccessResultWithActualListIsReturned() = runTest {

        quotesRepository.shouldReturnEmptyList = false
        quotesRepository.shouldReturnSuccessResult = true
        val actual = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        val expected = quotesRepository.getQuotesByPagination(null, pageLimit)
        assertEquals(expected, actual)
    }

    //
    @Test
    fun whenThereIsNoQuote_SuccessResultWithEmptyListIsReturned() = runTest {
        quotesRepository.shouldReturnEmptyList = true
        val actual = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        val expected = Result.success(emptyList<Quote>())
        assertEquals(expected, actual)
    }

    @Test
    fun whenThereIsAnError_ErrorResultWithItsCauseIsReturned() = runTest {
        quotesRepository.shouldReturnSuccessResult = false
        val actual = getAllQuotesByPagination.invoke(pageLimit = pageLimit)
        val expected = Result.Error()
        assertEquals(expected, actual)
    }

    @Test
    fun whenPageLimitIsNegative_ErrorResultWithItsCauseIsReturned() = runTest {
        val result = getAllQuotesByPagination.invoke(pageLimit = -1)
        assertIs<Result.Error>(result)
        assertIs<ErrorEntity.Unexpected>(result.errorEntity)
        assertIs<IllegalArgumentException>((result.errorEntity as ErrorEntity.Unexpected).e)
    }
}
