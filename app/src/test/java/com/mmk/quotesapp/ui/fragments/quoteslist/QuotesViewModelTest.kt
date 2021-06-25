package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.model.Result
import com.mmk.quotesapp.ui.base.UiState
import com.mmk.quotesapp.util.MainCoroutineRule
import com.mmk.quotesapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class QuotesViewModelTest {


    private lateinit var quotesViewModel: QuotesViewModel

    @Mock
    private lateinit var getAllQuotesByPaginationUseCase: GetQuotesByPaginationUseCase

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun init() {
        quotesViewModel = QuotesViewModel(getAllQuotesByPaginationUseCase)
    }

    @Test
    fun getAllQuotesLoadingTest() = mainCoroutineRule.runBlockingTest {

        whenever(
            getAllQuotesByPaginationUseCase(null, 5)
        ).thenReturn(Result.Success(emptyList()))

        mainCoroutineRule.pauseDispatcher()
        assertThat(quotesViewModel.uiState.getOrAwaitValue()).isEqualTo(UiState.Loading)
        mainCoroutineRule.resumeDispatcher()

    }


}

