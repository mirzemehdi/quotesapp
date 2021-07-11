package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mmk.quotesapp.R
import com.mmk.quotesapp.base.BaseTestApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import timber.log.Timber

@MediumTest
@RunWith(AndroidJUnit4::class)
class QuotesFragmentTest : KoinTest {

    private lateinit var scenario: FragmentScenario<QuotesFragment>


    lateinit var viewModel: QuotesViewModel


    @Before
    fun setup() {
        viewModel = Mockito.mock(QuotesViewModel::class.java)
        loadKoinModules(module { viewModel { viewModel } })
        whenever(viewModel.quotesListPagedData).thenReturn(MutableLiveData(PagingData.empty()))
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(newState = Lifecycle.State.STARTED)

    }

    @Test
    fun emptyView_isShownInUi_whenThereIsNoQuote() {

        Thread.sleep(5000)
    }

    @After
    fun cleanUp() {
        stopKoin()
    }


}