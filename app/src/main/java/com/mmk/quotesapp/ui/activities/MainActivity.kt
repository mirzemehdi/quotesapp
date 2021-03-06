package com.mmk.quotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  navController: NavController
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        onDestinationChanged()

    }

    private fun initView() {
        //setupBottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        with(binding) {
            bottomNavigation.setupWithNavController(navController)
            bottomNavigation.menu.findItem(R.id.addNewQuoteFragment).isCheckable = false
        }
    }

    private fun onDestinationChanged() {

        binding.addQuoteButton.setOnClickListener {
            binding.bottomNavigation.selectedItemId = R.id.addNewQuoteFragment

        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible =
                destination.id in arrayOf(R.id.quotesFragment, R.id.profileFragment)
            binding.addQuoteButton.isVisible = binding.bottomNavigation.isVisible
        }
    }

}
