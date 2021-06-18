package com.mmk.data.repository.quotes

import com.google.common.truth.Truth.assertThat
import com.mmk.data.remote.model.response.FakeRemoteDataSource
import com.mmk.data.util.MainCoroutineRule
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


@ExperimentalCoroutinesApi
class QuotesRepositoryTest {

    private lateinit var quotesRepository: QuotesRepository
    private lateinit var remoteDataSource: FakeRemoteDataSource

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {

        remoteDataSource = FakeRemoteDataSource()
        quotesRepository = QuotesRepositoryImpl(remoteDataSource, mainCoroutineRule.dispatcher)
    }


    @Test
    fun getQuotesByPagination_nullList_verifyErrorResult() = mainCoroutineRule.runBlockingTest {
        remoteDataSource.setList(null)
        val response = quotesRepository.getQuotesByPagination(null, 15)
        assertThat(response).isInstanceOf(Result.Error::class.java)


    }

    @Test
    fun getQuotesByPagination_emptyList_verifySuccessResult() = mainCoroutineRule.runBlockingTest {
        remoteDataSource.setList(emptyList())
        val response = quotesRepository.getQuotesByPagination(null, 15)
        assertThat(response).isInstanceOf(Result.Success::class.java)
        assertThat((response as Result.Success).data).isEqualTo(emptyList<Quote>())


    }

    @Test
    fun getQuotesByPagination_List_verifyList() = mainCoroutineRule.runBlockingTest {
        val quote1 = QuoteResponse(id = "1", "Mirze", "Test1", Date())
        val quote2 = QuoteResponse(id = "2", "Mehdi", "Test2", Date())
        val responseList = listOf(quote1, quote2)
        remoteDataSource.setList(responseList)
        val response = quotesRepository.getQuotesByPagination(null, 15)
        assertThat(response).isInstanceOf(Result.Success::class.java)
        val quoteList = responseList.map { it.mapToDomainModel() }
        assertThat((response as Result.Success).data).isEqualTo(quoteList)

    }


}