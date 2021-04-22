package com.mx.mundet.eats.ui.mvp.camera

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import coil.load
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityShowImageBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.message.MsgImageUser
import com.mx.mundet.eats.ui.message.MsgPathImage
import com.mx.mundet.eats.utils.MediaUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class ImageActivity : BaseActivity() {

    private lateinit var _binding: ActivityShowImageBinding
    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShowImageBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        EventBus.getDefault().register(this)
        initListener()
    }

    private fun loadImage() {
        fileName?.let {
            _binding.imgImageAccount.load(File((fileName!!)))
            //_binding.imgImageAccount.load(MediaUtils.base64ToImg(fileName!!))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setImagePath(msg: MsgPathImage) {
        fileName = msg.path
        loadImage()
    }

    private fun initListener() {
        _binding.toolbarShowImage.setNavigationOnClickListener {
            //TODO for popBackStack is necesary the ID of fragment more not ID action
            //findNavController().navigateUp()
            //findNavController().popBackStack(R.id.fragmentImage, true)
        }
        _binding.fabNextImage.setOnClickListener {
            //EventBus.getDefault().postSticky(MsgImageUser(img = MediaUtils.base64ToImg(fileName!!)))
            //activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        @JvmStatic
        fun newInstance(fileName: String) = ImageActivity().apply {
            this.fileName = fileName
        }

        @JvmStatic
        val TAG = ImageActivity::class.simpleName
    }
}