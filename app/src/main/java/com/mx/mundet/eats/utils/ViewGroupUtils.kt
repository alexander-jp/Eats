package com.mx.mundet.eats.utils

import android.view.ViewGroup

object ViewGroupUtils {
    fun VGWidthAndHeight(): ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

    fun VGProgress() : ViewGroup.LayoutParams {
        val vg = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        vg.height = 100
        vg.width = 100
        return  vg
    }
}