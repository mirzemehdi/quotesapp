package com.mmk.data.repository.quotes

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.mmk.data.util.MainCoroutineRule
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class QuotesPagingSourceTest {
    @Mock
    private lateinit var getAllQuotesByPaginationUseCase: GetQuotesByPaginationUseCase

    private lateinit var quotesPagingSource: QuotesPagingSource


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {

        quotesPagingSource = QuotesPagingSource(getAllQuotesByPaginationUseCase)
    }


    @Test
    fun loadAllQuotes_errorList_verifyReturnsError() = mainCoroutineRule.runBlockingTest {

        whenever(
            getAllQuotesByPaginationUseCase(
                anyOrNull(),
                anyOrNull()
            )
        ).thenReturn(Result.Error("Null list"))
        val actualAnswer = quotesPagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        )
        val expectedAnswerInstance = PagingSource.LoadResult.Error::class.java
        assertThat(actualAnswer).isInstanceOf(expectedAnswerInstance)

    }


    @Test
    fun loadAllQuotes_List_verifyReturnsSuccess() = mainCoroutineRule.runBlockingTest {
        val list = listOf(Quote(id = "1"), Quote(id = "2"))
        whenever(
            getAllQuotesByPaginationUseCase(anyOrNull(), anyOrNull())
        ).thenReturn(Result.Success(list))

        val actualAnswer = quotesPagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        )
        val expectedAnswerInstance = PagingSource.LoadResult.Page::class.java
        assertThat(actualAnswer).isInstanceOf(expectedAnswerInstance)
        val expectedAnswer = PagingSource.LoadResult.Page(list, null, list.last().id)
        assertThat(actualAnswer).isEqualTo(expectedAnswer)


    }

    @Test
    fun loadAllQuotes_emptyList_verifyIsEmpty() = mainCoroutineRule.runBlockingTest {
        whenever(
            getAllQuotesByPaginationUseCase(anyOrNull(), anyOrNull())
        ).thenReturn(Result.Success(emptyList()))

        val actualAnswer = quotesPagingSource.load(
            PagingSource.LoadParams.Refresh(null, 10, false)
        )

        val expectedAnswer = PagingSource.LoadResult.Page(emptyList(), null, null)
        assertThat(actualAnswer).isEqualTo(expectedAnswer)


    }


}