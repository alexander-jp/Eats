package com.mx.mundet.eats.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isInternetAvailable(ctx : Context) : Boolean{
        var mOnline = false
        val cManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cManager.activeNetworkInfo
        if(network!=null && network.isConnected){
            mOnline = true
        }
        return mOnline
    }
}