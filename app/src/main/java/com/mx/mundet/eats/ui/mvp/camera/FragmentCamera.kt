package com.mx.mundet.eats.ui.mvp.camera

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentNewCameraBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.dialog.DialogCameraImage
import com.mx.mundet.eats.ui.ext.addAlertFragment
import com.mx.mundet.eats.ui.ext.addFragment
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.selector.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class FragmentCamera : BaseFragment(R.layout.fragment_new_camera) {

    var nameFile: String? = null

    var fotoApp: Fotoapparat? = null

    var isFrontal: Boolean = false
    private lateinit var _binding: FragmentNewCameraBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewCameraBinding.bind(view)
        this.nameFile = arguments?.getString("nameFile")
        initListeners()
        _binding.btTakePicture.isEnabled = true
    }


    private fun startFotoApp() {
        fotoApp?.stop()
        fotoApp =
            Fotoapparat(context = requireContext(),
                view = _binding.camView,
                cameraConfiguration = CameraConfiguration(
                    flashMode = firstAvailable(
                        off()
                    ),
                    pictureResolution = {
                        var res: Resolution? = null
                        for (r in this) {
                            Log.d("$TAG Resolutions", "${r.area}")
                            if (r.area in 400 * 1000..800 * 1000) {
                                //Log.d("Resolutions", "Selected -> ${r.area}")
                                res = r
                                break
                            }
                        }
                        res ?: this.first()
                    }
                ),
                cameraErrorCallback = {
                    it.printStackTrace()
                    startFotoApp()
                }
            )
        fotoApp?.start()
        _binding.toolbarCamera.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

    }


    private fun initListeners() {
        _binding.btTakePicture.setOnClickListener {
            val newFile = createFile()
            fotoApp?.takePicture()?.saveToFile(newFile)?.whenAvailable {
                val file = File(newFile.path)
                //addAlertFragment(DialogCameraImage.newInstance(newFile.path), DialogCameraImage.TAG!!)
                val bundle = bundleOf("path" to newFile.path)
                findNavController().navigate(R.id.action_global_fragmentImage, bundle)
            }
        }
        _binding.toolbarCamera.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        _binding.btnSwitchCamera.setOnClickListener {
            if (isFrontal) {
                fotoApp?.switchTo(lensPosition = back(), CameraConfiguration())
                isFrontal = false
            } else {
                isFrontal = true
                fotoApp?.switchTo(lensPosition = front(), CameraConfiguration())
            }
        }
    }

    private fun createFile(): File {

        return File(requireActivity().getExternalFilesDir(null), nameFile ?: "pic" + SimpleDateFormat("yyMMddHHmmss").format(Date()) + ".jpg")
    }

    override fun onResume() {
        startFotoApp()
        super.onResume()
    }

    override fun onDestroyView() {
        fotoApp?.stop()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(nameFile: String?) = FragmentCamera().apply {
            this.nameFile = nameFile
        }

        @JvmStatic
        val TAG = FragmentCamera::class.simpleName

    }
}
