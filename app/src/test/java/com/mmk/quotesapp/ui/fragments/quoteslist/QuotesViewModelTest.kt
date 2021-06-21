package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.google.common.truth.Truth.assertThat
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.quotesapp.util.MainCoroutineRule
import com.mmk.quotesapp.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class QuotesViewModelTest {


    private lateinit var quotesViewModel: QuotesViewModel

    @Mock
    private lateinit var getAllQuotesByPaginationUseCase: GetQuotesByPaginationUseCase

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
    fun getAllQuotesByPaginationTransformation()=mainCoroutineRule.runBlockingTest {
        val expectedList = listOf(Quote(id = "1","sdfs","sf",), Quote(id = "3","sdfsdf","sdfsdfsd"))
        whenever(
            getAllQuotesByPaginationUseCase(null, 5)
        ).thenReturn(Result.Success(expectedList))


        val actualPagedData = quotesViewModel.quotesListPagedData.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ItemDiffCallBack(),
            updateCallback = MyListUpdateCallback(),
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        differ.submitData(actualPagedData)

        advanceUntilIdle()
        assertThat(expectedList).isEqualTo(differ.snapshot().items)
    }


    internal class MyListUpdateCallback : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }

    internal class ItemDiffCallBack : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }


}

