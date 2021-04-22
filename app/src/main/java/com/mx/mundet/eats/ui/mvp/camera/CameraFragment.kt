package com.mx.mundet.eats.ui.mvp.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentNewCameraBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.uriToFilePath
import com.mx.mundet.eats.ui.message.MsgPathImage
import com.mx.mundet.eats.ui.mvp.fileChooser.FileChooserActivity
import com.mx.mundet.eats.utils.MediaUtils
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import org.greenrobot.eventbus.EventBus
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
    var fotoApp: Fotoapparat? = null
    var isFrontal: Boolean = false
    var isFlashOn : Boolean = false
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
//                    flashMode = firstAvailable(
//                        off()
//                    ),
//                    focusMode = infinity(),
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
        //_binding.toolbarCamera.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

    }



    private fun initListeners() {
        _binding.btTakePicture.setOnClickListener {
            val newFile = createFile()
            fotoApp?.takePicture()?.saveToFile(newFile)?.whenAvailable {

                //val bm = MediaUtils.bitMaptoBase64(requireContext(), newFile.toUri())
                //addAlertFragment(DialogCameraImage.newInstance(newFile.path), DialogCameraImage.TAG!!)
                Log.e(TAG, "initListeners: ${newFile.path}")
                //val bundle = bundleOf("path" to newFile.path)
                EventBus.getDefault().postSticky(MsgPathImage(path = newFile.path))
                changeActivity(ImageActivity::class.java)
                //findNavController().navigate(R.id.action_global_fragmentImage, bundle)
            }
        }
        _binding.btGroupFlash.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when(checkedId){
                R.id.bt_flip_flash-> {
                    if(isChecked){
                        fotoApp?.updateConfiguration(CameraConfigurationCustom(Flash.On))
                        _binding.btFlipFlash.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_flash_on_24)
                    } else {
                        fotoApp?.updateConfiguration(CameraConfigurationCustom(Flash.On))
                        _binding.btFlipFlash.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_flash_off_24)
                    }
                }
            }
        }
        _binding.btFlipFlash.setOnClickListener {
            //findNavController().popBackStack()
        }
        _binding.btPickImageGallery.setOnClickListener {
            changeActivity(FileChooserActivity::class.java)
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), CODE_PICK_IMAGE)
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

        return File(requireActivity().getExternalFilesDir(null), nameFile ?: "pic" + SimpleDateFormat("yyMMddHHmmss").format(Date()) + ".jpg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            CODE_PICK_IMAGE -> {
                if (data?.data != null) {
                    //nameFile = data.data?.path
                    //val imageUri = data.data
                    //val bm = MediaUtils.bitMaptoBase64(requireContext(), imageUri)
//                    val exif = ExifInterface(data.data?.path)
//                    exif.getAttribute(ExifInterface.TAG_ORIENTATION)
//                    Log.e(TAG, "orientation image take: ${exif.getAttribute(ExifInterface.TAG_ORIENTATION)}")
                    //val bundle = bundleOf("path" to bm)
                    //findNavController().navigate(R.id.action_global_fragmentImage, bundle)
                    EventBus.getDefault().postSticky(MsgPathImage(path = uriToFilePath(checkNotNull(data.data))))
                    changeActivity(ImageActivity::class.java)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
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

        @JvmStatic
        val CODE_PICK_IMAGE : Int = 1

    }
}
