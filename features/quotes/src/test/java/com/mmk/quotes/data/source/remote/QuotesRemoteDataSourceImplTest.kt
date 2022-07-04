package com.mmk.quotes.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
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
    private lateinit var quotesRemoteDataSource: QuotesRemoteDataSource

    @BeforeEach
    internal fun setUp() {
        networkHandler = mockk()
        quotesRemoteDataSource = QuotesRemoteDataSourceImpl(networkHandler)
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
    }

    @DisplayName("Given There is a Network connection")
    @Nested
    inner class HasNetworkConnectionCase {
        @BeforeEach
        internal fun setUp() {
            every { networkHandler.hasNetworkConnection() } returns true
        }

        @DisplayName("When calling getQuotesByPagination")
        @Nested
        inner class GetQuotesByPagination {
            @DisplayName("Error Result is not type of NetworkConnection")
            @Test
            fun verifyErrorIsNotNetworkConnection() = runTest {
                val result = quotesRemoteDataSource.getQuotesByPagination("1", 10)
                assertThat(result).isInstanceOf(Result.Error::class.java)
                result as Result.Error
                assertThat(result.errorEntity).isNotInstanceOf(ErrorEntity.NetworkConnection::class.java)
            }
        }
    }
}
