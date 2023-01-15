package com.mmk.quotes.presentation.allquotes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.mmk.common.ui.UiState
import com.mmk.quotes.R
import com.mmk.quotes.domain.model.Quote
import com.mmk.testutils.koin.KoinTestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@MediumTest
@RunWith(AndroidJUnit4::class)
class QuotesFragmentTest {

    private lateinit var application: KoinTestApplication
    private lateinit var quotesPagingSource: PagingSource<String, Quote>
    private lateinit var quotesViewModel: QuotesViewModel

    @Before
    fun setUp() {
        application = ApplicationProvider.getApplicationContext()
        quotesPagingSource = mockk()
        quotesViewModel = spyk(QuotesViewModel { quotesPagingSource })
        application.injectModule(module { viewModel { quotesViewModel } })
    }

    @Test
    fun test_screen_works() {
        every { quotesViewModel.quotesList } returns MutableLiveData(PagingData.empty())
        launchFragmentInContainer<QuotesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.quoteTitle)).check(matches(withText(R.string.title_quotes)))
    }

    @Test
    fun progressBarIsShown_whenUiIsInLoadingState() {
        every { quotesViewModel.getQuotesUiState } returns MutableLiveData(UiState.Loading)
        every { quotesViewModel.quotesList } returns MutableLiveData(PagingData.empty())
        launchFragmentInContainer<QuotesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.progressBarQuotes))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun listViewIsShown_whenUiIsInHasDataState() {
        every { quotesViewModel.getQuotesUiState } returns MutableLiveData(UiState.HasData)
        every { quotesViewModel.quotesList } returns MutableLiveData(PagingData.empty())

        launchFragmentInContainer<QuotesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.progressBarQuotes)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.quotesRecyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun emptyViewIsShown_whenUiIsInNoDataState() {
        every { quotesViewModel.getQuotesUiState } returns MutableLiveData(UiState.NoData)
        every { quotesViewModel.quotesList } returns MutableLiveData(PagingData.empty())

        launchFragmentInContainer<QuotesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.progressBarQuotes)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.quotesRecyclerView)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.emptyView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun quoteListIsShown_whenThereAreQuotes() {
        every { quotesViewModel.getQuotesUiState } returns MutableLiveData(UiState.HasData)
        val quoteList = listOf(
            Quote("1", "TestAuthor", "Test"),
            Quote("2", "TestAuthor2", "Test2", true),
        )
        every { quotesViewModel.quotesList } returns MutableLiveData(PagingData.from(quoteList))

        launchFragmentInContainer<QuotesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.quotesRecyclerView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.quotesRecyclerView)).check { view, _ ->
            view as RecyclerView
            assert(view.adapter?.itemCount == quoteList.size)
        }
    }
}
