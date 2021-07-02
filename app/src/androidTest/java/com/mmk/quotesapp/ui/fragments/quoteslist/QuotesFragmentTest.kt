package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mmk.quotesapp.R
import com.mmk.quotesapp.base.BaseTestApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import timber.log.Timber

@MediumTest
@RunWith(AndroidJUnit4::class)
class QuotesFragmentTest {

    private lateinit var scenario: FragmentScenario<QuotesFragment>

    @Mock
    private lateinit var viewModel: QuotesViewModel



    @Before
     fun setup() {
        Timber.e(InstrumentationRegistry.getInstrumentation().targetContext.toString() )
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext as BaseTestApp
        whenever(viewModel.quotesListPagedData).thenReturn(MutableLiveData(PagingData.empty()))
        appContext.injectModule(module { single { viewModel } })
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
    }

    @Test
    fun emptyView_isShownInUi_whenThereIsNoQuote() {

//        Thread.sleep(5000)
    }


}