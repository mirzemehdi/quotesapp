package com.mmk.quotes.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class QuotesRemoteDataSourceImplTest {
    private lateinit var networkHandler: NetworkHandler
    private lateinit var quotesApiService: QuotesApiService
    private lateinit var quotesRemoteDataSource: QuotesRemoteDataSource

    @BeforeEach
    internal fun setUp() {
        networkHandler = mockk()
        quotesApiService = mockk()
        quotesRemoteDataSource = QuotesRemoteDataSourceImpl(networkHandler, quotesApiService)
    }

    @DisplayName("Given No Internet case")
    @Nested
    inner class NoNetworkConnectionCase {
        @BeforeEach
        internal fun setUp() {
            every { networkHandler.hasNetworkConnection() } returns false
        }

        @DisplayName("When calling getQuotesByPagination")
        @Nested
        inner class GetQuotesByPagination {

            @DisplayName("no network connection Error Result is returned")
            @Test
            fun verifyNoNetworkConnectionErrorResultIsReturned() = runTest {
                val result = quotesRemoteDataSource.getQuotesByPagination("1", 10)
                assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                assertThat(result.errorEntity).isInstanceOf(ErrorEntity.NetworkConnection::class.java)
            }
        }

        @DisplayName("When calling addNewQuote")
        @Nested
        inner class AddNewQuote {

            @DisplayName("no network connection Error Result is returned")
            @Test
            fun verifyNoNetworkConnectionErrorResultIsReturned() = runTest {
                val result = quotesRemoteDataSource.addNewQuote(NewQuoteRequest(id = "1"))
                assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                assertThat(result.errorEntity).isInstanceOf(ErrorEntity.NetworkConnection::class.java)
            }
        }
    }

    @DisplayName("Given There is a Network connection")
    @Nested
    inner class HasNetworkConnectionCase {
        private val exception = FirebaseFirestoreException(
            "Some error happened",
            FirebaseFirestoreException.Code.CANCELLED
        )

        @BeforeEach
        internal fun setUp() {
            every { networkHandler.hasNetworkConnection() } returns true
        }

        @DisplayName("When calling getQuotesByPagination")
        @Nested
        inner class GetQuotesByPagination {

            @DisplayName("Success Result with quoteList is returned")
            @Test
            fun verifySuccessResultWithQuoteListIsReturned() = runTest {
                val quoteList = listOf(QuoteResponse("1"), QuoteResponse("2"))
                coEvery { quotesApiService.getQuotesByPagination(any(), any()) } returns quoteList
                val result = quotesRemoteDataSource.getQuotesByPagination("1", 10)
                assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                assertThat(result.data).isEqualTo(quoteList)
            }

            @DisplayName("Error Result with ApiError is returned")
            @Test
            fun verifyErrorResultWithApiErrorIsReturned() = runTest {
                coEvery { quotesApiService.getQuotesByPagination(any(), any()) } throws exception
                val result = quotesRemoteDataSource.getQuotesByPagination("1", 10)
                assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                assertThat(result.errorEntity).isEqualTo(ErrorEntity.ApiError(exception = exception))
            }
        }

        @DisplayName("When calling addNewQuote")
        @Nested
        inner class AddNewQuote {
            private val newQuoteRequest = NewQuoteRequest(id = "1", author = "TestAuthor")

            @DisplayName("Success Result is returned")
            @Test
            fun verifySuccessResultIsReturned() = runTest {
                coEvery { quotesApiService.addNewQuote(any()) } returns Unit
                val result = quotesRemoteDataSource.addNewQuote(newQuoteRequest)
                assertThat(result).isInstanceOf(Result.Success::class.java)
                result as Result.Success
                assertThat(result.data).isEqualTo(Unit)
            }

            @DisplayName("Error Result with ApiError is returned")
            @Test
            fun verifyErrorResultWithApiErrorIsReturned() = runTest {
                coEvery { quotesApiService.addNewQuote(any()) } throws exception
                val result = quotesRemoteDataSource.addNewQuote(newQuoteRequest)
                assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                assertThat(result.errorEntity).isEqualTo(ErrorEntity.ApiError(exception = exception))
            }
        }
    }
}
