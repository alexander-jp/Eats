package com.mx.mundet.eats.ui.base

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.mx.mundet.eats.R
import com.mx.mundet.eats.utils.ViewGroupUtils
import com.mx.mundet.eats.ui.ext.colorAttr
import com.wang.avi.AVLoadingIndicatorView

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

open class BaseActivity : AppCompatActivity() {
    private var progress: AVLoadingIndicatorView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun initProgress(view: ViewGroup?) {
        view?.let {
            val relative = RelativeLayout(this)
            relative.gravity = Gravity.CENTER
            progress = AVLoadingIndicatorView(this)
            progress?.setIndicator(getString(R.string.name_progressAV))
            progress?.layoutParams = ViewGroupUtils.VGProgress()
            progress?.setIndicatorColor(colorAttr(R.attr.colorDotProgress))
            relative.layoutParams = ViewGroupUtils.VGWidthAndHeight()
            relative.addView(progress)
            it.addView(relative)
        }
    }

    fun showProgress(show :Boolean){
        if(show){
            if(!progress?.isShown!!){
                progress?.show()
            }
        }else{
            if(progress?.isShown!!){
                progress?.hide()
            }
        }
    }

}