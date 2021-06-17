package com.mmk.data.repository.quotes

import androidx.paging.PagingData
import androidx.paging.map
import com.google.common.truth.Truth.assertThat
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*


@ExperimentalCoroutinesApi
class QuotesRepositoryTest {

    private lateinit var quotesRepository: QuotesRepository
    private lateinit var remoteDataSource: FakeRemoteDataSource

    @Before
    fun init() {
        remoteDataSource = FakeRemoteDataSource()
        quotesRepository = QuotesRepositoryImpl(remoteDataSource)
    }


    @Test
    fun getQuotesByPagination_nullList_verifyErrorResult() = runBlockingTest {
        remoteDataSource.setList(null)
        val response = quotesRepository.getQuotesByPagination()
        assertThat(response).isInstanceOf(Result.Error::class.java)


    }

    @Test
    fun getQuotesByPagination_emptyList_verifySuccessResult() = runBlockingTest {
        remoteDataSource.setList(emptyList())
        val response = quotesRepository.getQuotesByPagination()
        assertThat(response).isInstanceOf(Result.Success::class.java)
        assertThat((response as Result.Success).data.first().map()).isEqualTo(
            PagingData.empty<Quote>()

        )


    }

    @Test
    fun getQuotesByPagination_List_verifyList() = runBlockingTest {
        val quote1 = QuoteResponse(id = "1", "Mirze", "Test1", Date())
        val quote2 = QuoteResponse(id = "2", "Mehdi", "Test2", Date())
        val list = listOf<QuoteResponse>(QuoteResponse())
        remoteDataSource.setList(emptyList())
        val response = quotesRepository.getQuotesByPagination()
        assertThat(response).isInstanceOf(Result.Success::class.java)


    }


}