package com.mx.mundet.eats.ui.mvp.camera

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import coil.load
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentShowImageBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.message.MsgImageUser
import com.mx.mundet.eats.utils.MediaUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class ImageFragment : BaseFragment(R.layout.fragment_show_image){

    private lateinit var _binding : FragmentShowImageBinding
    private var fileName : String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShowImageBinding.bind(view)

        this.fileName = arguments?.getString("path")
        loadImage()
        initListener()
    }

    private fun loadImage(){
        fileName?.let {
            _binding.imgImageAccount.load(MediaUtils.base64ToImg(fileName!!))
        }
    }

    private fun initListener(){
        _binding.toolbarShowImage.setNavigationOnClickListener {
            //TODO for popBackStack is necesary the ID of fragment more not ID action
            findNavController().navigateUp()
            //findNavController().popBackStack(R.id.fragmentImage, true)
        }
        _binding.fabNextImage.setOnClickListener {
            EventBus.getDefault().postSticky(MsgImageUser(img = MediaUtils.base64ToImg(fileName!!)))
            activity?.finish()
        }
    }


     companion object{
          @JvmStatic
          fun newInstance(fileName : String) = ImageFragment().apply{
              this.fileName = fileName
          }

          @JvmStatic
          val TAG = ImageFragment::class.simpleName
     }
}