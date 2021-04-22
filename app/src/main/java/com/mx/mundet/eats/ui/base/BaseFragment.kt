package com.mx.mundet.eats.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.ui.ext.colorAttr
import com.mx.mundet.eats.ui.mvp.main.MainActivity
import com.mx.mundet.eats.utils.ViewGroupUtils
import com.wang.avi.AVLoadingIndicatorView

/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

abstract class BaseFragment : Fragment {

    private var progress: AVLoadingIndicatorView? = null
    private var myProgress: Dialog? = null

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initProgressDialog()
    }

    fun setTitleToobar(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }


    @SuppressLint("InflateParams")
    private fun initProgressDialog() {
        val myView = layoutInflater.inflate(R.layout.custom_progress_dialog, null)
        myProgress = Dialog(requireContext(), R.style.cornerDialog)
        myProgress?.setCancelable(false)
        myProgress?.setContentView(myView)
        myProgress?.create()
    }

    fun showDialogProgress(status: Boolean) {
        if (status) {
            if (!myProgress?.isShowing!!) {
                myProgress?.show()
            }
        } else {
            if (myProgress?.isShowing!!) {
                myProgress?.dismiss()
            }
        }
    }


    fun initProgress(view: ViewGroup?) {
        view?.let {
            val relative = RelativeLayout(requireContext())
            relative.gravity = Gravity.CENTER
            progress = AVLoadingIndicatorView(requireContext())
            progress?.setIndicator(getString(R.string.name_progressAV))
            progress?.layoutParams = ViewGroupUtils.VGProgress()
            progress?.setIndicatorColor(colorAttr(R.attr.colorDotProgress))
            relative.layoutParams = ViewGroupUtils.VGWidthAndHeight()
            relative.addView(progress)
            it.addView(relative)
        }
    }

    fun showProgress(show: Boolean) {
        if (show) {
            if (!progress?.isShown!!) {
                progress?.show()
            }
        } else {
            if (progress?.isShown!!) {
                progress?.hide()
            }
        }
    }
}