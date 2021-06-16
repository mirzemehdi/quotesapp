package com.mmk.data.repository.quotes

import com.mmk.data.remote.RemoteDataSource
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class QuotesRepositoryTest{

    private lateinit var quotesRepository: QuotesRepository
    private lateinit var remoteDataSource:RemoteDataSource

    @Before
    fun init(){
        remoteDataSource=FakeRemoteDataSource()
        quotesRepository=QuotesRepositoryImpl(remoteDataSource)
    }




}