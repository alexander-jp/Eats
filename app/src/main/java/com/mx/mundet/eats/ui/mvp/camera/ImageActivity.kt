package com.mx.mundet.eats.ui.mvp.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import coil.load
import com.mx.mundet.eats.databinding.ActivityShowImageBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.message.MsgImageUser
import com.mx.mundet.eats.ui.message.MsgPathImage
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserActivity
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
    private var newFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShowImageBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        EventBus.getDefault().register(this)
        initListener()
    }

    private fun loadImage() {
        newFile?.let {
            _binding.imgImageAccount.load(newFile)
            //_binding.imgImageAccount.load(MediaUtils.base64ToImg(fileName!!))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun setImagePath(msg: MsgPathImage) {
        newFile = File(msg.path)
        loadImage()
    }

    private fun initListener() {
        _binding.toolbarShowImage.setNavigationOnClickListener {
            finish()
        }
        _binding.fabNextImage.setOnClickListener {
            EventBus.getDefault().postSticky(MsgImageUser(img = newFile!! ))
            val i = Intent()
            val imgUri = Uri.fromFile(newFile)
            Log.e(TAG, "URI IMG $imgUri")
            i.data = imgUri
            setResult(RESULT_OK, i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        @JvmStatic
        fun newInstance(fileName: String) = ImageActivity().apply {

        }

        @JvmStatic
        val TAG = ImageActivity::class.simpleName
    }
}