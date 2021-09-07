package com.mx.mundet.eats.ui.mvp.camera

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentNewCameraBinding
import com.mx.mundet.eats.ui.base.BaseFragment
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

@RequiresApi(Build.VERSION_CODES.Q)
class CameraFragment : BaseFragment(R.layout.fragment_new_camera) {

    var nameFile: String? = null
    private var fotoApp: Fotoapparat? = null
    private var isFrontal: Boolean = false
    private lateinit var _binding: FragmentNewCameraBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //activity?.setTheme(R.style.Theme_Eats)
        _binding = FragmentNewCameraBinding.bind(view)
        this.nameFile = arguments?.getString("nameFile")
        initListeners()
    }


    private fun startFotoApp() {
        fotoApp?.stop()
        fotoApp =
            Fotoapparat(context = requireContext(),
                view = _binding.camView,
                cameraConfiguration = CameraConfiguration(
                    flashMode = firstAvailable(
                        off(),
                        autoFlash(),
                        torch()
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
    }



    private fun initListeners() {
        _binding.btTakePicture.setOnClickListener {
            val newFile = createFile()

            fotoApp?.takePicture()?.saveToFile(newFile)?.whenAvailable {
                val arg = bundleOf("path" to newFile.path)
                findNavController().navigate(R.id.action_global_imageFragment, arg)
            }
        }

        _binding.btnBackCamera.setOnClickListener {
            activity?.finish()
        }

        _binding.btnSwitchCamera.setOnClickListener {
            if (isFrontal) {
                fotoApp?.switchTo(lensPosition = back(), CameraConfiguration())
                isFrontal = false
                _binding.btnSwitchCamera.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_camera_front_24))
            } else {
                isFrontal = true
                fotoApp?.switchTo(lensPosition = front(), CameraConfiguration())
                _binding.btnSwitchCamera.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_flip_camera_ios_24))
            }
        }
    }

    private fun createFile(): File {

        return File(requireActivity().getExternalFilesDir(null), nameFile ?: "IMG_" + SimpleDateFormat("yyMMddHHmmss").format(Date()) + ".jpg")
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
        fun newInstance(nameFile: String?) = CameraFragment().apply {
            this.nameFile = nameFile
        }

        @JvmStatic
        val TAG = CameraFragment::class.simpleName

    }
}
