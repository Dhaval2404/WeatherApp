package com.github.dhaval2404.weatherapp.ui.main_screen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.ActivityMainBinding
import com.github.dhaval2404.weatherapp.ui.base.BaseActivity

/**
 * Single activity for all app fragments
 *
 * Activity uses Jetpack Navigation components
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mNavController: NavController

    override fun getLayout() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = mViewDataBinding.appbar.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            mNavController.graph,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        toolbar.setupWithNavController(mNavController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() ||
            super.onSupportNavigateUp()
    }
}
