package com.mmk.quotesapp.ui

import MainView
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.chrynan.navigation.ExperimentalNavigationApi
import com.chrynan.navigation.compose.rememberNavigator
import com.chrynan.navigation.goBack
import com.mmk.root.TopLevelDestination

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigator = rememberNavigator(initialDestination = TopLevelDestination.QUOTES)
            BackHandler {
                if (navigator.canGoBack()) navigator.goBack()
                else finish()
            }
            MainView(navigator = navigator)
        }
    }
}
