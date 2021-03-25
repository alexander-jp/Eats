package com.mx.mundet.eats.ui.mvp

import android.os.Bundle
import android.view.View
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentSettingBinding
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.mvp.main.MainActivity

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class FragmentSetting : BaseFragment(R.layout.fragment_setting) {
    private var _binding: FragmentSettingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingBinding.bind(view)
        initSettings()
    }

    private fun initSettings(){
        //(activity as MainActivity).supportActionBar?.title = getString(R.string.text_title_item_settings)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentSetting().apply {

        }

        @JvmStatic
        val TAG = FragmentSetting::class.simpleName
    }
}