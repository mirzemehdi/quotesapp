package com.mmk.quotes.data.repository

import com.google.common.truth.Truth
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.mappers.QuoteMapper
import com.mmk.quotes.data.source.remote.FakeQuotesRemoteDataSource
import com.mmk.quotes.domain.model.Quote
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@ExperimentalCoroutinesApi

internal class QuotesRepositoryImplTest {

    private lateinit var quoteMapper: QuoteMapper
    private lateinit var networkHandler: NetworkHandler
    private lateinit var quotesRemoteDataSource: FakeQuotesRemoteDataSource
    private lateinit var quotesRepositoryImpl: QuotesRepositoryImpl

    @BeforeEach
    fun setUp() {
        quoteMapper = QuoteMapper()
        networkHandler = mockk()
        quotesRemoteDataSource = FakeQuotesRemoteDataSource(networkHandler)
        quotesRepositoryImpl = QuotesRepositoryImpl(quotesRemoteDataSource, quoteMapper)
    }

    @DisplayName("Given No Internet Case")
    @Nested
    inner class NoInternetCase {

        @BeforeEach
        internal fun setUp() {
            every { networkHandler.hasNetworkConnection() } returns false
        }

        @DisplayName("When Getting quotes by pagination")
        @Nested
        inner class GetQuotesByPagination {

            @DisplayName("NoNetworkConnection Error is returned")
            @Test
            fun noNetworkConnectionErrorIsReturned() = runTest {
                val result = quotesRepositoryImpl.getQuotesByPagination(null, 10)
                Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                Truth.assertThat(result.errorEntity)
                    .isInstanceOf(ErrorEntity.networkConnection().javaClass)
            }
        }

        @DisplayName("When adding new quote")
        @Nested
        inner class AddNewQuote {

            @DisplayName("NoNetworkConnection Error is returned")
            @Test
            fun noNetworkConnectionErrorIsReturned() = runTest {
                val result = quotesRepositoryImpl.addNewQuote(Quote(id = "1"))
                Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                Truth.assertThat(result.errorEntity)
                    .isInstanceOf(ErrorEntity.networkConnection().javaClass)
            }
        }
    }

    @DisplayName("Has Network Connection")
    @Nested
    inner class HasNetworkConnection {
        @BeforeEach
        internal fun setUp() {
            every { networkHandler.hasNetworkConnection() } returns true
        }

        @DisplayName("When Getting quotes by pagination")
        @Nested
        inner class GetQuotesByPagination {

            @DisplayName(
                "ApiError with exception is returned, " +
                    "if some exception happens in network side"
            )
            @Test
            fun apiErrorWithExceptionIsReturned() = runTest {
                quotesRemoteDataSource.shouldThrowException = true
                val result = quotesRepositoryImpl.getQuotesByPagination(null, 10)
                Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                Truth.assertThat(result.errorEntity)
                    .isInstanceOf(ErrorEntity.apiError().javaClass)
            }

            @DisplayName("Success Result with empty list is returned, if there are no quotes")
            @Test
            fun emptyListReturnedIfThereAreNoQuotes() = runTest {
                val result = quotesRepositoryImpl.getQuotesByPagination(null, 10)
                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                Truth.assertThat(result.data).isEmpty()
            }

            @DisplayName("Success Result with quote list is returned, if there are quotes")
            @Test
            fun successResultWithQuotesAreReturnedIfThereAreQuotes() = runTest {
                val quoteList = listOf(Quote(id = "1"), Quote(id = "2"), Quote(id = "3"))
                quoteList.forEach {
                    quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(it))
                }
                val result = quotesRepositoryImpl.getQuotesByPagination(null, 10)
                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                Truth.assertThat(result.data).isEqualTo(quoteList)
            }

            @DisplayName("Verify pagination limit is correct")
            @Test
            fun verifyPaginationLimitIsCorrect() = runTest {
                val quoteList = listOf(Quote(id = "1"), Quote(id = "2"), Quote(id = "3"))
                quoteList.forEach {
                    quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(it))
                }
                val result = quotesRepositoryImpl.getQuotesByPagination(null, 2)
                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                Truth.assertThat(result.data).hasSize(2)
            }

            @DisplayName("Verify pagination starts after pagination index=quoteId")
            @Test
            fun verifyPaginationStartsAfterPageIndex() = runTest {
                val quoteList = listOf(Quote(id = "1"), Quote(id = "2"), Quote(id = "3"))
                quoteList.forEach {
                    quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(it))
                }

                // Get quotes after id=2
                val result = quotesRepositoryImpl.getQuotesByPagination("2", 2)
                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                Truth.assertThat(result.data).hasSize(1)
                Truth.assertThat(result.data).isEqualTo(quoteList.subList(2, 3))
            }
        }

        @DisplayName("When Adding new quote")
        @Nested
        inner class AddNewQuote {

            @DisplayName(
                "ApiError with exception is returned, " +
                    "if some problem happens in network side"
            )
            @Test
            fun apiErrorWithExceptionIsReturned() = runTest {
                quotesRemoteDataSource.shouldThrowException = true
                val result = quotesRepositoryImpl.addNewQuote(Quote(id = "1"))
                Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                Truth.assertThat(result.errorEntity)
                    .isInstanceOf(ErrorEntity.apiError().javaClass)
            }

            @DisplayName("Success Result with Empty Data is returned, if added successfully")
            @Test
            fun successResultWithEmptyDataIsReturnedIfAddedSuccessfully() = runTest {
                val randomId = UUID.randomUUID().toString()
                val quote = Quote(id = randomId)
                val result =
                    quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(quote))

                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                Truth.assertThat(result.data).isEqualTo(Unit)
                Truth.assertThat(quotesRemoteDataSource.contains(randomId)).isTrue()
            }
        }
    }
}
