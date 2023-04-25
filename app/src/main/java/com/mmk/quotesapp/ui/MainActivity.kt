package com.mmk.quotesapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mmk.common.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

//    private fun initView() {
//        // setupBottomNavigation
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        with(binding) {
//            bottomNavigation.setupWithNavController(navController)
//            bottomNavigation.menu.findItem(R.id.nav_graph_add_quote_xml).isCheckable = false
//        }
//    }

//    private fun onDestinationChanged() {
//
//        binding.addQuoteButton.setOnClickListener {
//            binding.bottomNavigation.selectedItemId = R.id.nav_graph_add_quote_xml
//        }
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.bottomNavigation.isVisible =
//                destination.id in arrayOf(R.id.quotesFragment, R.id.profileFragment)
//            binding.addQuoteButton.isVisible = binding.bottomNavigation.isVisible
//        }
//    }
}
