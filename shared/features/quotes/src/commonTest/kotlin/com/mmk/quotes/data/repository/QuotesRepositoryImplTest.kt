package com.mmk.quotes.data.repository

import app.cash.turbine.test
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.mappers.QuoteMapper
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSourceImpl
import com.mmk.quotes.data.source.remote.apiservice.FakeQuotesApiServiceImpl
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.model.QuoteUtil
import kotlinx.coroutines.test.runTest
import kotlin.test.*

internal class QuotesRepositoryImplTest {

    private lateinit var quoteMapper: QuoteMapper
    private lateinit var networkHandler: FakeNetworkHandler
    private lateinit var quotesRemoteDataSource: QuotesRemoteDataSource
    private lateinit var quotesApiService: FakeQuotesApiServiceImpl
    private lateinit var quotesRepository: QuotesRepositoryImpl

    private class FakeNetworkHandler : NetworkHandler {
        var hasConnection: Boolean = true
        override fun hasNetworkConnection() = hasConnection
    }

    @BeforeTest
    fun setUp() {
        quoteMapper = QuoteMapper()
        networkHandler = FakeNetworkHandler()
        quotesApiService = FakeQuotesApiServiceImpl()
        quotesRemoteDataSource = QuotesRemoteDataSourceImpl(networkHandler, quotesApiService)
        quotesRepository = QuotesRepositoryImpl(quotesRemoteDataSource, quoteMapper)
    }

    // No Network connection state
    @Test
    fun noNetworkConnection_addQuote_ErrorIsReturned() = runTest {
        networkHandler.hasConnection = false
        val result = quotesRepository.addNewQuote(QuoteUtil.newQuoteWithConstantId())
        assertIs<Result.Error>(result)
        assertEquals(ErrorEntity.networkConnection(), result.errorEntity)
    }

    @Test
    fun noNetworkConnection_getQuotes_ErrorIsReturned() = runTest {
        networkHandler.hasConnection = false
        val result = quotesRepository.getQuotesByPagination(null, 10)
        assertIs<Result.Error>(result)
        assertEquals(ErrorEntity.networkConnection(), result.errorEntity)
    }

    // Has Network connection state

    @Test
    fun apiThrowsException_addQuote_apiErrorWithExceptionResultIsReturned() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = true
        val result = quotesRepository.addNewQuote(QuoteUtil.newQuoteWithConstantId())
        assertIs<Result.Error>(result)
        assertEquals(
            ErrorEntity.apiError(exception = quotesApiService.exception),
            result.errorEntity
        )
    }

    @Test
    fun quoteIsAddedSuccessfully() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false
        val quote = QuoteUtil.newQuoteWithConstantId()
        val result = quotesRepository.addNewQuote(quote)

        assertIs<Result.Success<Unit>>(result)
        assertEquals(Result.EMPTY, result)
        val quotesListResult = quotesRepository.getQuotesByPagination(null, 10)
        quotesListResult as Result.Success<List<Quote>>
        assertTrue(quotesListResult.data.contains(quote))
    }

    @Test
    fun apiThrowsException_getQuotes_apiErrorWithExceptionResultIsReturned() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = true
        val result = quotesRepository.getQuotesByPagination(null, 10)
        assertIs<Result.Error>(result)
        assertEquals(
            ErrorEntity.apiError(exception = quotesApiService.exception),
            result.errorEntity
        )
    }

    //
    @Test
    fun getQuotes_emptyListReturnedIfThereAreNoQuotes() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false
        val result = quotesRepository.getQuotesByPagination(null, 10)
        assertIs<Result.Success<List<Quote>>>(result)
        assertEquals(emptyList(), result.data)
    }

    //
    @Test
    fun successResultWithQuotesAreReturnedIfThereAreQuotes() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false
        val quoteList = QuoteUtil.getQuoteListWithUniqueIds(5)
        quoteList.forEach {
            quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(it))
        }
        val result = quotesRepository.getQuotesByPagination(null, 10)
        assertIs<Result.Success<List<Quote>>>(result)
        assertEquals(quoteList, result.data)
    }

    @Test
    fun verify_getQuotes_PaginationLimitIsCorrect() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false
        val quoteList = QuoteUtil.getQuoteListWithUniqueIds(5)
        quoteList.forEach {
            quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(it))
        }
        val pageLimit = 2
        val result = quotesRepository.getQuotesByPagination(null, pageLimit)
        assertIs<Result.Success<List<Quote>>>(result)
        assertEquals(pageLimit, result.data.size)
    }

    @Test
    fun verify_getQuotes_PaginationStartsAfterPageIndexAndSortedByTime() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false
        val quoteList = mutableListOf<Quote>()
        (1..5).forEach { index ->
            val quote = Quote(id = "ID: $index", timeStamp = index * 1000L)
            quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(quote))
            quoteList.add(quote)
        }
        val sortedList = quoteList.sortedByDescending { it.timeStamp }

        val firstPageResult = quotesRepository.getQuotesByPagination(null, 2)

        assertIs<Result.Success<List<Quote>>>(firstPageResult)
        assertEquals(sortedList.subList(0, 2), firstPageResult.data)
        val nextPageIndex = firstPageResult.data.last().timeStamp

        val secondPageResult = quotesRepository.getQuotesByPagination(nextPageIndex.toString(), 2)

        assertIs<Result.Success<List<Quote>>>(secondPageResult)
        assertEquals(sortedList.subList(2, 4), secondPageResult.data)
    }

    @Test
    fun verify_observeFirstPageQuotes_whenNewQuoteIsAdded() = runTest {
        networkHandler.hasConnection = true
        quotesApiService.shouldThrowException = false

        quotesRepository.listenForDataChange(5).test {
            var result = awaitItem()

            assertIs<Result.Success<List<Quote>>>(result)
            assertEquals(emptyList(), result.data)

            val quote = QuoteUtil.newQuoteWithConstantId()
            quotesRepository.addNewQuote(quote)

            result = awaitItem()
            assertIs<Result.Success<List<Quote>>>(result)
            assertEquals(listOf(quote), result.data)

            cancel()
        }
    }
}
