package com.mx.mundet.eats.ui.mvp.setting

import android.os.Bundle
import com.mx.mundet.eats.databinding.ActivitySettingBinding
import com.mx.mundet.eats.ui.base.BaseActivity

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class SettingActivity : BaseActivity() {
    private lateinit var _binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }



    private fun initSettings(){
        //(activity as MainActivity).supportActionBar?.title = getString(R.string.text_title_item_settings)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingActivity().apply {

        }

        @JvmStatic
        val TAG = SettingActivity::class.simpleName
    }
}