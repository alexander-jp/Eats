package com.mx.mundet.eats.ui.mvp.All

import android.os.Bundle
import android.view.View
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentAllBinding
import com.mx.mundet.eats.ui.base.BaseFragment

/**
 * Created by Alexander Juárez with Date 26/03/2021
 * @author Alexander Juárez
 */

class AllFragment : BaseFragment(R.layout.fragment_all) {

    private lateinit var _binding : FragmentAllBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllBinding.bind(view)
    }
}