package com.mmk.quotes.presentation.allquotes

import com.google.common.truth.Truth
import com.mmk.common.ui.UiState
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import com.mmk.testutils.lifecycle.getOrAwaitValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
internal class QuotesViewModelTest {

    private lateinit var quotesViewModel: QuotesViewModel

    @BeforeEach
    internal fun setUp() {
        quotesViewModel = QuotesViewModel()
    }

    @Test
    fun `verify getAllQuotes UI state is Loading when first time viewModel is created`() {
        val uiState = quotesViewModel.getAllQuotesUiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.Loading)
    }
}
