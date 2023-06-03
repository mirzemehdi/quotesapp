package com.mmk.quotes.presentation.addnewquote

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.google.common.truth.Truth
import com.mmk.core.model.Result
import com.mmk.quotes.R
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import com.mmk.testutils.koin.KoinTestApplication
import com.mmk.testutils.lifecycle.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@MediumTest
@RunWith(AndroidJUnit4::class)
class AddNewQuoteFragmentTest {

    private lateinit var application: KoinTestApplication
    private lateinit var addQuotesVM: AddNewQuoteVM
    private lateinit var addNewQuoteUseCase: AddNewQuote

    @Before
    fun setUp() {
        application = ApplicationProvider.getApplicationContext()
        addNewQuoteUseCase = mockk()
        addQuotesVM = spyk(AddNewQuoteVM(addNewQuoteUseCase))
        application.injectModule(module { viewModel { addQuotesVM } })
    }

    @Test
    fun test_screen_works() {
        launchFragmentInContainer<AddNewQuoteFragment>(themeResId = R.style.AppTheme)
        Espresso.onView(ViewMatchers.withId(R.id.addNewQuoteButton))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_quoteTextAndAuthor_isShown() {
        val author = "TestAuthor"
        val quoteText = "TestQuoteText"

        addQuotesVM.quoteAuthor.postValue(author)
        addQuotesVM.quoteText.postValue(quoteText)

        launchFragmentInContainer<AddNewQuoteFragment>(themeResId = R.style.AppTheme)
        Espresso.onView(ViewMatchers.withId(R.id.newQuoteEditText))
            .check(ViewAssertions.matches(ViewMatchers.withText(quoteText)))

        Espresso.onView(ViewMatchers.withId(R.id.newQuoteAuthorEditText))
            .check(ViewAssertions.matches(ViewMatchers.withText(author)))
    }

    @Test
    fun test_loadingProgressIsShown_WhenClickSubmitButton() {
        val author = "TestAuthor"
        val quoteText = "TestQuoteText"
        coEvery { addNewQuoteUseCase.invoke(any()) } coAnswers {
            delay(2000)
            Result.EMPTY
        }

        launchFragmentInContainer<AddNewQuoteFragment>(themeResId = R.style.AppTheme)

        Espresso.onView(ViewMatchers.withId(R.id.newQuoteAuthorEditText)).perform(ViewActions.typeText(author))
        Espresso.onView(ViewMatchers.withId(R.id.newQuoteEditText)).perform(ViewActions.typeText(quoteText))
        Espresso.onView(ViewMatchers.withId(R.id.addNewQuoteButton)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_inputDetails_isSavedToViewModel() {
        val author = "TestAuthor"
        val quoteText = "TestQuoteText"
        coEvery { addNewQuoteUseCase.invoke(any()) } coAnswers {
            delay(2000)
            Result.EMPTY
        }
        launchFragmentInContainer<AddNewQuoteFragment>(themeResId = R.style.AppTheme)

        Espresso.onView(ViewMatchers.withId(R.id.newQuoteAuthorEditText)).perform(ViewActions.typeText(author))
        Espresso.onView(ViewMatchers.withId(R.id.newQuoteEditText)).perform(ViewActions.typeText(quoteText))

        UiThreadStatement.runOnUiThread {
            Truth.assertThat(addQuotesVM.quoteText.getOrAwaitValue()).isEqualTo(quoteText)
            Truth.assertThat(addQuotesVM.quoteAuthor.getOrAwaitValue()).isEqualTo(author)
        }
    }
}
