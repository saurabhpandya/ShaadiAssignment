package com.fidato.shaadiassignment.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.fidato.shaadiassignment.R
import com.fidato.shaadiassignment.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName

    private lateinit var binding: MainActivityBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var host: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        setupNavigationHost()
    }

    private fun setupNavigationHost() {

        host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

//        val navController = host.navController

//        appBarConfiguration = AppBarConfiguration(setOf(R.id.setupFragment))
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

}