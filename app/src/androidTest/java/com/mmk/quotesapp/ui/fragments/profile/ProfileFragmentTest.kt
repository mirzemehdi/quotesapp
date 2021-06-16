package com.mmk.quotesapp.ui.fragments.profile

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.mmk.quotesapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest

@RunWith(AndroidJUnit4::class)
@MediumTest
class ProfileFragmentTest : AutoCloseKoinTest() {

    private lateinit var scenario: FragmentScenario<ProfileFragment>


    @Before
    fun setUp() {
//        startKoin {
//            modules(emptyList())
//        }


        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(newState = Lifecycle.State.STARTED)


    }

    @Test
    fun notAuthorizedUser_viewInUi() {
        onView(withId(R.id.emailLoginEditText)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}