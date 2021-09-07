package com.mx.mundet.eats.ui.mvp.camera

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mx.mundet.eats.R
import com.mx.mundet.eats.ui.base.BaseActivity

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class CameraActivity : BaseActivity() {

    private val navControllers by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_camera)?.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = intent.extras?.getInt("orientation")
        if(requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            Log.d("Orientation","was portrait")
        }else{
            Log.d("Orientation","was landscape")
        }
        if(orientation == ORIENTATION_PORTRAIT){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        }
        else if(orientation == ORIENTATION_LANDSCAPE){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            configChanged = true
        }

        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_camera)
        supportActionBar?.hide()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), RC_PERMISOS
                )

            } else {
                setFragment()
            }
        } else {
            setFragment()
        }
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

    private fun setFragment() {

        val nameFile: String? = if (intent.hasExtra(EXTRA_NAME_FILE)) {
            intent.getStringExtra(EXTRA_NAME_FILE)
        } else {
            null
        }
        val arg = bundleOf("nameFile" to nameFile)
        navControllers?.navigate(R.id.action_global_fragmentCamera, arg)
    }

    override fun onBackPressed() {
        customBack()
    }

    private fun customBack(){
        Log.e(TAG, "customBack ID ->: ${navControllers?.currentDestination?.id}")
        when(navControllers?.currentDestination?.id){
            R.id.fragmentCamera-> super.onBackPressed()
            R.id.imageFragment-> navControllers?.navigateUp()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraActivity().apply {

        }

        @JvmStatic
        val TAG = CameraActivity::class.simpleName

        private var configChanged = false

        @JvmStatic
        private val RC_PERMISOS = 203
        val EXTRA_NAME_FILE = "nameFile"
        val ORIENTATION_PORTRAIT = 0
        val ORIENTATION_LANDSCAPE = 1
    }
}