package com.mx.mundet.eats.ui.mvp.main

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityMainBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.mvp.setting.SettingActivity
import com.mx.mundet.eats.ui.mvp.recent.RecentFragment
import com.mx.mundet.eats.ui.mvp.home.HomeFragment

class MainActivity : BaseActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val appBar = findViewById<MaterialToolbar>(R.id.toolbar_home)
        setSupportActionBar(appBar)
        //val navController = findNavController(R.id.content_main)
        val navController = checkNotNull(supportFragmentManager.findFragmentById(R.id.content_main)).findNavController()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_all, R.id.nav_recent, R.id.nav_settings), _binding.drawerLayout)
        setupActionBarWithNavController(navController = navController, configuration = appBarConfiguration)
        _binding.navView.setupWithNavController(navController)
        //TODO for change icon menu drawer support theme dark
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        appBar.setNavigationIcon(R.drawable.ic_baseline_ballot_24)
        //appBar.setNavigationIconTint(R.color.white)
//        val toggle = ActionBarDrawerToggle(
//            this,
//            _binding.drawerLayout,
//            appBar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        _binding.drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        _binding.navView.setupWithNavController(navController!!)
//        initListener()
    }

//    override fun onBackPressed() {
//        if (_binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            _binding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            finish()
//        }
//        super.onBackPressed()
//    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initListener(){
        _binding.navView.setNavigationItemSelectedListener {
            val stack = supportFragmentManager.findFragmentById(R.id.content_main)?.findNavController()?.currentDestination
            when(it.itemId){
                R.id.nav_home -> {
                    if (stack?.label != HomeFragment.TAG) {
                        supportFragmentManager.findFragmentById(R.id.content_main)?.findNavController()?.navigate(R.id.action_global_fragmentHome)
                    }
                }
                R.id.nav_all -> showToast("All")
                R.id.nav_recent -> {
                    if (stack?.label != RecentFragment.TAG) {
                        supportFragmentManager.findFragmentById(R.id.content_main)?.findNavController()?.navigate(R.id.action_global_recentFragment)
                    }
                }
                R.id.nav_settings-> {
                    if (stack?.label != SettingActivity.TAG) {
                        supportFragmentManager.findFragmentById(R.id.content_main)?.findNavController()?.navigate(R.id.action_global_fragmentSetting)
                    }
                }
            }
            _binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

    }
}