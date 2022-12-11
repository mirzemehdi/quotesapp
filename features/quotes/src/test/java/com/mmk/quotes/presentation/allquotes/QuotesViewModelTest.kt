package com.mmk.quotes.presentation.allquotes

import androidx.paging.LoadState
import com.google.common.truth.Truth
import com.mmk.common.ui.ErrorMessage
import com.mmk.common.ui.UiState
import com.mmk.core.model.ErrorEntity
import com.mmk.quotes.data.repository.QuotesPagingSource
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.util.PagingException
import com.mmk.testutils.coroutine.CoroutinesTestExtension
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import com.mmk.testutils.lifecycle.getOrAwaitValue
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestExtension::class)
internal class QuotesViewModelTest {

    private lateinit var quotesViewModel: QuotesViewModel
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination
    private lateinit var quotesPagingSourceFactory: () -> QuotesPagingSource

    @BeforeEach
    internal fun setUp() {
        getAllQuotesByPagination = mockk()
        quotesPagingSourceFactory = { QuotesPagingSource(getAllQuotesByPagination) }
        quotesViewModel = QuotesViewModel(quotesPagingSourceFactory)
    }

    @Test
    fun `getQuotes UI state is Loading when first time viewModel is created`() {
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getQuotes uiState is HasData when there are quotes`() = runTest {
        quotesViewModel.onPageAdapterLoadingStateChanged(
            LoadState.NotLoading(false), 4
        )
        advanceUntilIdle()

        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.HasData)
    }

    @Test
    fun `getQuotes uiState is NoData when there are no quotes`() = runTest {
        quotesViewModel.onPageAdapterLoadingStateChanged(LoadState.NotLoading(false), 0)
        advanceUntilIdle()
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.NoData)
    }

    @Nested
    internal inner class GivenErrorOnPageAdapterLoadingStateChanged {
        @Test
        fun `uiState is Error`() = runTest {
            quotesViewModel.onPageAdapterLoadingStateChanged(
                LoadState.Error(PagingException(ErrorEntity.networkConnection()))
            )
            advanceUntilIdle()

            val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
            Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
        }

        @Test
        fun `errorMessage is not empty if errorType is not NoNetworkConnection`() =
            runTest {
                quotesViewModel.onPageAdapterLoadingStateChanged(
                    LoadState.Error(PagingException(ErrorEntity.apiError()))
                )
                advanceUntilIdle()

                val errorMessage =
                    ErrorMessage.ResourceId(com.mmk.common.ui.R.string.msg_unknown_error_occurred)
                val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
                Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
                Truth.assertThat((uiState as UiState.Error).errorMessage).isEqualTo(errorMessage)
            }

        @Test
        fun ` then error type is Unexpected if not PagingException`() =
            runTest {
                quotesViewModel.onPageAdapterLoadingStateChanged(
                    LoadState.Error(Exception("Some error occurred, and not covered with PagingException"))
                )
                advanceUntilIdle()

                val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
                Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
            }

        @Test
        fun `given no network then noNetworkConnection is true`() = runTest {
            quotesViewModel.onPageAdapterLoadingStateChanged(
                LoadState.Error(PagingException(ErrorEntity.networkConnection()))
            )
            advanceUntilIdle()

            val noNetworkConnectionEvent = quotesViewModel.noNetworkConnectionEvent.getOrAwaitValue()

            Truth.assertThat(noNetworkConnectionEvent).isNotNull()
        }

        @Test
        fun `given no network then errorMessage is null`() = runTest {
            quotesViewModel.onPageAdapterLoadingStateChanged(
                LoadState.Error(PagingException(ErrorEntity.networkConnection()))
            )
            advanceUntilIdle()

            val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
            Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
            Truth.assertThat((uiState as UiState.Error).errorMessage).isNull()
        }
    }
}
