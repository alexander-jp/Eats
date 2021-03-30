package com.mx.mundet.eats.ui.mvp.recent

import android.os.Bundle
import android.view.View
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentRecentBinding
import com.mx.mundet.eats.ui.base.BaseFragment

/**
 * Created by Alexander Juárez with Date 25/03/2021
 * @author Alexander Juárez
 */

class RecentFragment : BaseFragment(R.layout.fragment_recent) {

    private lateinit var _binding: FragmentRecentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecentBinding.bind(view)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecentFragment().apply {

        }

        @JvmStatic
        val TAG = RecentFragment::class.simpleName
    }
}