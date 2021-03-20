package com.mx.mundet.eats.ui.mvp.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityMainBinding
import com.mx.mundet.eats.ui.ext.addFragment
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.mvp.home.FragmentHome

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding


    @SuppressLint("RestrictedApi", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val appBar = findViewById<MaterialToolbar>(R.id.toolbar_home)

        setSupportActionBar(appBar)
        //TODO for change icon menu drawer support theme dark
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        appBar.setNavigationIcon(R.drawable.ic_baseline_ballot_24)
//        appBar.setNavigationIconTint(R.color.white)
        val toggle = ActionBarDrawerToggle(
            this,
            _binding.drawerLayout,
            appBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        _binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        initListener()
    }

    override fun onResume() {
        addFragment(FragmentHome.newInstance(), R.id.content_main, false)
        super.onResume()
    }

    override fun onBackPressed() {
        if (_binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {

            finish()
        }
        super.onBackPressed()
    }

    private fun initListener(){
        _binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_home-> addFragment(FragmentHome.newInstance(), R.id.content_main, false)
                R.id.item_all -> showToast("All")
                R.id.item_home_recent -> showToast("Recent")
                R.id.item_settings-> showToast("Settings")
            }
            _binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
}