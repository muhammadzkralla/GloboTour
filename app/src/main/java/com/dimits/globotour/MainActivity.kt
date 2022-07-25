package com.dimits.globotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var toolbar        : Toolbar
    private lateinit var navController  : NavController
    private lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        toolbar         = findViewById(R.id.activity_main_toolbar)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)

        // Get NavHostFragment and NavController
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController   = navHostFrag.navController

        val topLevelDestinations = setOf(R.id.fragmentCityList,R.id.fragmentFavoriteList)
        val appBarConfigurationLayout = AppBarConfiguration(topLevelDestinations)

        toolbar.setupWithNavController(navController,appBarConfigurationLayout)

        bottomNavigationView.setupWithNavController(navController)


    }
}