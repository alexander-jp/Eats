package com.mx.mundet.eats.ui.mvp.camera

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import coil.load
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentSettingBinding
import com.mx.mundet.eats.databinding.FragmentShowImageBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.ext.showToast
import java.io.File

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class FragmentImage : BaseFragment(R.layout.fragment_show_image){

    private lateinit var _bindig : FragmentShowImageBinding
    private var fileName : String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bindig = FragmentShowImageBinding.bind(view)

        this.fileName = arguments?.getString("path")
        loadImage()
        initListener()
    }

    private fun loadImage(){
        fileName?.let {
            _bindig.imgImageAccount.load(File(it))
        }
    }

    private fun initListener(){
        _bindig.toolbarShowImage.setNavigationOnClickListener {
            //TODO for popBackStack is necesary the ID of fragment more not ID action
            findNavController().navigateUp()
            //findNavController().popBackStack(R.id.fragmentImage, true)
        }
    }

     companion object{
          @JvmStatic
          fun newInstance(fileName : String) = FragmentImage().apply{
              this.fileName = fileName
          }

          @JvmStatic
          val TAG = FragmentImage::class.simpleName
     }
}