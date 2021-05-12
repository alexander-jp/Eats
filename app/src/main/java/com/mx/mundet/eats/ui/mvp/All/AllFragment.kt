package com.mx.mundet.eats.ui.mvp.All

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentAllBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.mvp.fileChooser.FileChooserActivity

/**
 * Created by Alexander Juárez with Date 26/03/2021
 * @author Alexander Juárez
 */

@RequiresApi(Build.VERSION_CODES.M)
class AllFragment : BaseFragment(R.layout.fragment_all) {

    private lateinit var _binding : FragmentAllBinding
    private var fingerPrintManager : FingerprintManager? = null
    private var keyguardManager : KeyguardManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllBinding.bind(view)
        checkPermission()
        if(checkSensorFingerPrint()){
            showToast("Sensor Finger Print exist")
        }
        initListeners()
    }


    private fun initListeners(){
        _binding.imvFingerPrintUser.setOnClickListener {

        }
    }


    private fun checkSensorFingerPrint() : Boolean {
        keyguardManager = context?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fingerPrintManager = context?.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        return checkNotNull(fingerPrintManager?.isHardwareDetected)
    }


    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.USE_FINGERPRINT), RC_FINGER_PRINT )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            RC_FINGER_PRINT-> showSnackBar("Permiso concedido Finger Print")
            else -> activity?.finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
     companion object{
          @JvmStatic
          fun newInstance() = AllFragment().apply{

          }

          @JvmStatic
          val TAG = AllFragment::class.simpleName
         @JvmStatic
         val RC_FINGER_PRINT : Int = 1
     }
}