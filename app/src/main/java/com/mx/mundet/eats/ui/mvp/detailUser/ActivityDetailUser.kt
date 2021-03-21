package com.mx.mundet.eats.ui.mvp.detailUser

import android.os.Bundle
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityDetailUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity

/**
 * Created by Alexander Juárez with Date 21/03/2021
 * @author Alexander Juárez
 */

class ActivityDetailUser : BaseActivity() {

    private lateinit var _binding : ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initSettings()
    }

    private fun initSettings(){
    }
}