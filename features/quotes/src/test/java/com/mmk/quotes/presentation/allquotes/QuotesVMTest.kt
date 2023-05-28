package com.mmk.quotes.presentation.allquotes

import androidx.paging.LoadState
import com.google.common.truth.Truth
import com.mmk.common.ui.util.UiMessage
import com.mmk.core.model.ErrorEntity
import com.mmk.quotes.data.repository.QuotesPagingSource
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.util.PagingException
import com.mmk.testutils.coroutine.CoroutinesTestExtension
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestExtension::class)
internal class QuotesVMTest {

    private lateinit var quotesViewModel: QuotesVM
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination
    private lateinit var quotesPagingSourceFactory: () -> QuotesPagingSource

    @BeforeEach
    internal fun setUp() {
        getAllQuotesByPagination = mockk()
        quotesPagingSourceFactory = { QuotesPagingSource(getAllQuotesByPagination) }
        quotesViewModel = QuotesVM(quotesPagingSourceFactory)
    }

    @Test
    fun `getQuotes UI state is Loading when first time viewModel is created`() = runTest {
        val uiState = quotesViewModel.getQuotesUiState.first()
        Truth.assertThat(uiState).isEqualTo(QuotesUiState.Loading)
    }

    @Test
    fun `getQuotes uiState is HasData when there are quotes`() = runTest {
        quotesViewModel.onPageAdapterLoadingStateChanged(
            LoadState.NotLoading(false), 4
        )
        advanceUntilIdle()

        val uiState = quotesViewModel.getQuotesUiState.first()
        Truth.assertThat(uiState).isEqualTo(QuotesUiState.HasData)
    }

    //
    @Test
    fun `getQuotes uiState is NoData when there are no quotes`() = runTest {
        quotesViewModel.onPageAdapterLoadingStateChanged(LoadState.NotLoading(false), 0)
        advanceUntilIdle()
        val uiState = quotesViewModel.getQuotesUiState.first()
        Truth.assertThat(uiState).isEqualTo(QuotesUiState.Empty)
    }

    //
    @Nested
    internal inner class GivenErrorOnPageAdapterLoadingStateChanged {
        @Test
        fun `errorMessage is not null`() = runTest {
            val errorMessage = getErrorMessageValueAfterAction {
                quotesViewModel.onPageAdapterLoadingStateChanged(
                    LoadState.Error(PagingException(ErrorEntity.networkConnection()))
                )
                advanceUntilIdle()
            }

            Truth.assertThat(errorMessage).isNotNull()
        }

        @Test
        fun ` then error message is Unexpected if not PagingException`() =
            runTest {

                val errorMessage = getErrorMessageValueAfterAction {
                    quotesViewModel.onPageAdapterLoadingStateChanged(
                        LoadState.Error(Exception("Some error occurred, and not covered with PagingException"))
                    )
                    advanceUntilIdle()
                }
                val expectedErrorMessage =
                    UiMessage.Resource(MR.strings.msg_unknown_error_occurred)
                Truth.assertThat(errorMessage).isEqualTo(expectedErrorMessage)
            }

        @Test
        fun `given no network then then is noNetworkConnection message`() = runTest {
            val errorMessage = getErrorMessageValueAfterAction {
                quotesViewModel.onPageAdapterLoadingStateChanged(
                    LoadState.Error(PagingException(ErrorEntity.networkConnection()))
                )
                advanceUntilIdle()
            }

            Truth.assertThat(errorMessage).isNotNull()
            val expectedErrorMessage =
                UiMessage.Resource(MR.strings.msg_no_network_connection)
            Truth.assertThat(errorMessage).isEqualTo(expectedErrorMessage)
        }

        private fun TestScope.getErrorMessageValueAfterAction(block: () -> Unit): UiMessage? {
            var errorMessage: UiMessage? = null
            val job = launch {
                errorMessage = quotesViewModel.uiMessage.first()
            }
            block()
            job.cancel()
            return errorMessage
        }
    }
}
