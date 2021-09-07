package com.mx.mundet.eats.ui.mvp.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import coil.load
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentShowImageBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import org.greenrobot.eventbus.EventBus
import java.io.File

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class ImageFragment : BaseFragment(R.layout.fragment_show_image) {

    private lateinit var _binding: FragmentShowImageBinding
    private var newFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShowImageBinding.bind(view)
        newFile = File(checkNotNull(arguments?.getString("path")))
        loadImage()
        initListener()
    }


    private fun loadImage() {
        setTitleToolbar()
        newFile?.let {
            _binding.imgImageAccount.load(newFile)
        }
    }

    private fun initListener() {
        _binding.fabNextImage.setOnClickListener {
            val i = Intent()
            i.data = Uri.fromFile(newFile)
            activity?.setResult(Activity.RESULT_OK, i)
            activity?.finish()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        @JvmStatic
        fun newInstance(fileName: String) = ImageFragment().apply {

        }

        @JvmStatic
        val TAG = ImageFragment::class.simpleName
    }
}