package com.dariwan.kupin.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val nav: BottomNavigationView = findViewById(R.id.bottom_nav_menu)
        nav.itemIconTintList = null

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragmentRefrigerator ||
                destination.id == R.id.fragmentRecipe ||
                destination.id == R.id.fragmentRecomm ||
                destination.id == R.id.fragmentUser
            ) {
                showBottomNavigation()
            } else {
                hideBottomNavigation()
            }
        }

        setupWithNavController(binding.bottomNavMenu, navController)
    }

    private fun showBottomNavigation() {
        binding.bottomNavMenu.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        binding.bottomNavMenu.visibility = View.GONE
        binding.fab.visibility = View.GONE
    }
}