package com.mmk.quotes.presentation.addnewquote

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.core.model.Result
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class AddNewQuoteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val tag = "AddNewQuoteScreenTag"
    private lateinit var addNewQuote: AddNewQuote
    private lateinit var viewModel: AddNewQuoteVM

    @Before
    fun setUp() {
        addNewQuote = mockk()
        viewModel = AddNewQuoteVM(addNewQuoteUseCase = addNewQuote)

        composeTestRule.setContent {
            MyApplicationTheme {
                AddNewQuoteRoute(viewModel = viewModel, onBackPress = {})
            }
        }
    }

    @Test
    fun firstOpening_textAndAuthorTextIsEmpty() {

        composeTestRule
            .addQuoteTextInputNode()
            .assertTextEquals("")

        composeTestRule
            .addQuoteAuthorInputNode()
            .assertTextEquals("")
    }

    @Test
    fun firstOpening_buttonIsVisible() {
        composeTestRule
            .addNewQuoteButton()
            .assertIsDisplayed()
    }

    @Test
    fun firstOpening_progressIsNotVisible() {
        composeTestRule
            .addNewQuoteProgress()
            .assertDoesNotExist()
    }

    @Test
    fun quoteTextIsShownCorrectly() {
        val expected = "ExampleQuoteText"
        viewModel.onQuoteTextChanged(expected)
        composeTestRule
            .addQuoteTextInputNode()
            .assertTextEquals(expected)
    }

    @Test
    fun quoteAuthorIsShownCorrectly() {
        val expected = "ExampleAuthor"
        viewModel.onAuthorTextChanged(expected)
        composeTestRule
            .addQuoteAuthorInputNode()
            .assertTextEquals(expected)
    }

    @Test
    fun newQuoteAddingProcess_progressIsVisible() {
        coEvery { addNewQuote.invoke(any()) } coAnswers {
            delay(2000)
            Result.EMPTY
        }
        viewModel.addQuote()
        composeTestRule
            .addNewQuoteProgress()
            .assertIsDisplayed()
    }

    @Test
    fun newQuoteAddingProcess_buttonIsInvisible() {
        coEvery { addNewQuote.invoke(any()) } coAnswers {
            delay(1000)
            Result.EMPTY
        }
        viewModel.addQuote()

        composeTestRule
            .addNewQuoteButton()
            .assertDoesNotExist()
    }

    @Test
    fun newQuoteAddedSuccessState_buttonIsVisibleProgressIsInvisible() {
        coEvery { addNewQuote(any()) } coAnswers {
            delay(300)
            Result.EMPTY
        }
        viewModel.addQuote()
        // Loading state
        composeTestRule
            .addNewQuoteProgress()
            .assertIsDisplayed()

        composeTestRule
            .addNewQuoteButton()
            .assertDoesNotExist()

        Thread.sleep(300)

        // Completed state
        composeTestRule
            .addNewQuoteProgress()
            .assertDoesNotExist()

        composeTestRule
            .addNewQuoteButton()
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.addQuoteTextInputNode() =
        this.onNodeWithTag("addQuoteText", true)

    private fun ComposeContentTestRule.addQuoteAuthorInputNode() =
        this.onNodeWithTag("addQuoteAuthor", true)

    private fun ComposeContentTestRule.addNewQuoteButton() =
        this.onNodeWithTag("addNewQuoteButton", true)

    private fun ComposeContentTestRule.addNewQuoteProgress() =
        this.onNodeWithTag("addNewQuoteProgress", true)
}
