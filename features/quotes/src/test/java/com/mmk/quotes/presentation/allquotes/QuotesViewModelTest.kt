package com.mmk.quotes.presentation.allquotes

import com.google.common.truth.Truth
import com.mmk.common.ui.UiState
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.testutils.coroutine.CoroutinesTestExtension
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import com.mmk.testutils.lifecycle.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestExtension::class)
internal class QuotesViewModelTest {

    private lateinit var quotesViewModel: QuotesViewModel
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination

    @BeforeEach
    internal fun setUp() {
        getAllQuotesByPagination = mockk()
        quotesViewModel = QuotesViewModel(getAllQuotesByPagination)
    }

    @Test
    fun `verify getQuotes UI state is Loading when first time viewModel is created`() {
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `verify quotesList is Empty when first time viewModel is created`() = runTest {
        val actualList = quotesViewModel.quotesList.getOrAwaitValue()
        Truth.assertThat(actualList).isEmpty()
    }

    @Test
    fun `verify quotesList is empty when there are no quotes`() = runTest {
        val quoteList = emptyList<Quote>()
        coEvery { getAllQuotesByPagination(any(), any()) } returns Result.success(quoteList)
        advanceUntilIdle()
        val actualList = quotesViewModel.quotesList.getOrAwaitValue()
        Truth.assertThat(actualList).isEmpty()
    }

    @Test
    fun `verify quotesList is not empty when there are quotes`() = runTest {
        val quoteList = listOf(Quote("1"), Quote(id = "2"))
        coEvery { getAllQuotesByPagination(any(), any()) } returns Result.success(quoteList)
        advanceUntilIdle()
        val actualList = quotesViewModel.quotesList.getOrAwaitValue()
        Truth.assertThat(actualList).isEqualTo(quoteList)
    }

    @Test
    fun `verify getQuotes uiState is HasData when there are quotes`() = runTest {
        val quoteList = listOf(Quote("1"), Quote(id = "2"))
        coEvery { getAllQuotesByPagination(any(), any()) } returns Result.success(quoteList)
        advanceUntilIdle()
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.HasData)
    }

    @Test
    fun `verify getQuotes uiState is NoData when there are no quotes`() = runTest {
        val quoteList = emptyList<Quote>()
        coEvery { getAllQuotesByPagination(any(), any()) } returns Result.success(quoteList)
        advanceUntilIdle()
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.NoData)
    }

    @Test
    fun `given no network when getting quotes then noNetworkConnection is true`() = runTest {
        coEvery {
            getAllQuotesByPagination(
                any(),
                any()
            )
        } returns Result.Error(ErrorEntity.networkConnection())
        advanceUntilIdle()
        val noNetworkConnectionEvent = quotesViewModel.noNetworkConnectionEvent.getOrAwaitValue()

        Truth.assertThat(noNetworkConnectionEvent).isNotNull()
    }

    @Test
    fun `given some error when getting quotes then uiState is Error`() = runTest {
        coEvery {
            getAllQuotesByPagination(
                any(),
                any()
            )
        } returns Result.Error()
        advanceUntilIdle()
        val uiState = quotesViewModel.getQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
    }
}
