package com.mmk.quotes.presentation.addnewquote

import com.google.common.truth.Truth
import com.mmk.common.ui.SingleEvent
import com.mmk.common.ui.UiState
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import com.mmk.testutils.coroutine.CoroutinesTestExtension
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import com.mmk.testutils.lifecycle.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestExtension::class)
internal class AddNewQuoteVMTest {

    private lateinit var viewModel: AddNewQuoteVM
    private lateinit var addNewQuoteUseCase: AddNewQuote

    @BeforeEach
    internal fun setUp() {
        addNewQuoteUseCase = mockk()
        viewModel = AddNewQuoteVM(addNewQuoteUseCase)
    }

    @Test
    fun `addQuote UiState is in HasData state when viewModel is created`() = runTest {
        val uiState = viewModel.uiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.HasData)
    }

    @Test
    fun `addQuote UiState is in Loading when addNewQuote is called`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } coAnswers {
            delay(2000)
            Result.Error()
        }
        viewModel.addQuote()
        advanceTimeBy(1000)
        val uiState = viewModel.uiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `addQuote UiState is in HasData when addNewQuote result is returned successfully`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.EMPTY
        viewModel.addQuote()
        advanceUntilIdle()
        val uiState = viewModel.uiState.getOrAwaitValue()
        Truth.assertThat(uiState).isEqualTo(UiState.HasData)
    }

    @Test
    fun `onAddQuote event is fired when addNewQuote result is returned successfully`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.EMPTY
        viewModel.addQuote()
        advanceUntilIdle()
        val onAddQuote = viewModel.onQuoteAdded.getOrAwaitValue()
        Truth.assertThat(onAddQuote).isEqualTo(SingleEvent(Unit))
    }

    @Test
    fun `addQuote UiState is in Error state when addNewQuote result is failed`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.Error()
        viewModel.addQuote()
        advanceUntilIdle()
        val uiState = viewModel.uiState.getOrAwaitValue()
        Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
    }
    @Test
    fun `noNetworkConnection event is fired when addNewQuote result is failed because of network connection`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.Error(ErrorEntity.networkConnection())
        viewModel.addQuote()
        advanceUntilIdle()

        val uiState = viewModel.uiState.getOrAwaitValue()
        Truth.assertThat(uiState).isInstanceOf(UiState.Error::class.java)
        val noNetworkConnection = viewModel.noNetworkConnectionEvent.getOrAwaitValue()
        Truth.assertThat(noNetworkConnection).isEqualTo(SingleEvent(Unit))
    }
}
