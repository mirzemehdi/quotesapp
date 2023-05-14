package com.mmk.quotes.presentation.addnewquote

import com.google.common.truth.Truth
import com.mmk.common.ui.ErrorMessage
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import com.mmk.testutils.coroutine.CoroutinesTestExtension
import com.mmk.testutils.lifecycle.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
    fun `addQuote ui state is not in loading when viewModel is created`() = runTest {
        val uiState = viewModel.uiState.first()
        Truth.assertThat(uiState.isLoading).isEqualTo(false)
    }

    @Test
    fun `addQuote UiState is in Loading when addNewQuote is called`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } coAnswers {
            delay(2000)
            Result.Error()
        }
        viewModel.addQuote()
        advanceTimeBy(1000)
        val uiState = viewModel.uiState.first()
        Truth.assertThat(uiState.isLoading).isEqualTo(true)
    }

    //
    @Test
    fun `addQuote UiState is not loading when addNewQuote result is returned successfully`() =
        runTest {
            coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.EMPTY
            viewModel.addQuote()
            advanceUntilIdle()
            val uiState = viewModel.uiState.first()
            Truth.assertThat(uiState.isLoading).isEqualTo(false)
        }

    //
    @Test
    fun `quote result is not null when addNewQuote result is returned successfully`() = runTest {
        val author = "testAuthor"
        val text = "testText"
        val quote = Quote(author = author, text = text)
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.EMPTY
        viewModel.onAuthorTextChanged(author)
        viewModel.onQuoteTextChanged(text)
        viewModel.addQuote()
        advanceUntilIdle()
        val newAddedQuote = viewModel.uiState.first().newAddedQuote
        Truth.assertThat(newAddedQuote).isEqualTo(quote.copy(id = newAddedQuote?.id ?: ""))
    }

    @Test
    fun `addQuote UiState has Error message when addNewQuote result is failed`() = runTest {
        coEvery { addNewQuoteUseCase.invoke(any()) } returns Result.Error()
        var errorMessage: ErrorMessage? = null
        val job = launch {
            errorMessage = viewModel.errorMessage.first()
        }
        viewModel.addQuote()
        advanceUntilIdle()
        Truth.assertThat(errorMessage).isNotNull()
        job.cancel()
    }
}
