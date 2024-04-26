package com.dariwan.kupin.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dariwan.kupin.R
import com.dariwan.kupin.core.utils.NotificationUtils
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityMainBinding
import com.dariwan.kupin.view.refrigenerator.RefrigeneratorViewModel
import com.dariwan.kupin.view.refrigenerator.addmaterial.AddMaterialActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = obtainViewModel(this as AppCompatActivity)

        setupBottomNav()
        setupFab()
        checkDataMaterialDate()

    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddMaterialActivity::class.java)
            startActivity(intent)
        }
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

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private fun checkDataMaterialDate() {
        mainViewModel.getAllMaterials().observe(this, Observer { materials ->
            materials.forEach { material ->
                val formatter = DateTimeFormatter.ofPattern("dd - MM - yyyy")
                val currentDate = LocalDate.now()
                val materialDate = LocalDate.parse(material.date, formatter)

                Log.e("coba_worker_1", "current date: $currentDate, material date: $materialDate, status: ${material.notificationSent}")

                if (materialDate != null){
                    val difference = ChronoUnit.DAYS.between(currentDate, materialDate)
                    if (difference <= 5 && difference.toInt() != 0 && material.notificationSent == 0){
                        val notificationMessage = "Persediaan ${material.name} akan habis dalam $difference hari."
                        val notificationId = material.id
                        material.notificationSent = 1
                        mainViewModel.updateNotificationSent(material.id, 1)
                        NotificationUtils.showNotification(applicationContext, "Peringatan Persediaan", notificationMessage, notificationId)
                    }
                }
            }
        })
    }
}