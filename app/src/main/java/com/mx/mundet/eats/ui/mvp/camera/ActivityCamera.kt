package com.mx.mundet.eats.ui.mvp.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavArgs
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import com.mx.mundet.eats.R

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class ActivityCamera : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.show()
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

    private fun setFragment() {

        val nameFile: String? = if (intent.hasExtra(EXTRA_NAME_FILE)) {
            intent.getStringExtra(EXTRA_NAME_FILE)
        } else {
            null
        }
        val arg = bundleOf("nameFile" to nameFile)
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_camera)?.findNavController()?.navigate(R.id.action_global_fragmentCamera, arg)
//        supportFragmentManager.beginTransaction().replace(R.id.container_cam, FragmentCamera.newInstance(nameFile)).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ActivityCamera().apply {

        }

        @JvmStatic
        val TAG = ActivityCamera::class.simpleName

        @JvmStatic
        private val RC_PERMISOS = 203
        val EXTRA_NAME_FILE = "nameFile"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                RC_PERMISOS -> setFragment()
            }
        } else {
            finish()
        }
    }
}