package com.mmk.quotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mmk.quotesapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController:NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //throw RuntimeException("Test Crash") // Force a crash



        initView()

        onDestinationChanged()

    }

    private fun initView() {
        //setupBottomNavigation
        bottomNavigation.setupWithNavController(navController)

        //TODO fix this issue disable selecting index 1 item
        bottomNavigation.menu.findItem(R.id.addNewQuoteFragment).isCheckable=false
    }

    private fun onDestinationChanged() {

        addQuoteButton.setOnClickListener {
            bottomNavigation.selectedItemId=R.id.addNewQuoteFragment

        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.isVisible=destination.id in arrayOf(R.id.quotesFragment,R.id.profileFragment)
            addQuoteButton.isVisible=bottomNavigation.isVisible
        }
    }

}
