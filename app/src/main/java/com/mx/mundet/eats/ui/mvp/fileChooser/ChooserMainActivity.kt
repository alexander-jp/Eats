package com.mx.mundet.eats.ui.mvp.fileChooser

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.showToast

/**
 * Created by Alexander Juárez with Date 12/05/2021
 * @author Alexander Juárez
 */

class ChooserMainActivity : BaseActivity() {

    private val navControllers by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_container_chooser)?.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chooser)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        checkPermissions()

    }

    private fun setFragment(){
        navControllers?.navigate(R.id.action_global_fragmentListFolder)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), RC_PERMISOS
                )

            } else {
                setFragment()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home-> {
                customBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun customBack(){
        when(navControllers?.currentDestination?.id){
            R.id.fragmentListFolder-> super.onBackPressed()
            R.id.imageListFragment-> navControllers?.navigateUp()
            R.id.imageFragment-> navControllers?.navigateUp()
        }
    }

    override fun onBackPressed() {
        customBack()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                RC_PERMISOS -> setFragment()
            }
        } else {
            finish()
        }
    }

    companion object {

        @JvmStatic
        private val RC_PERMISOS = 203
    }

}