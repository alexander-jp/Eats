package com.mx.mundet.eats.ui.base

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mx.mundet.eats.ui.model.ConfigModelApp
import com.mx.mundet.eats.utils.JsonUtils

/**
 * Created by Alexander Juárez with Date 28/03/2021
 * @author Alexander Juárez
 */

class SessionBean(@Transient private val mPreference: SharedPreferences) {

    var config : ConfigModelApp = ConfigModelApp()

    init {
        val jsonSession = mPreference.getString(SessionBean::class.java.simpleName, "")
        val sessionBean = JsonUtils.readJson(jsonSession!!, SessionBean::class.java)
        putData(sessionBean)
    }

    fun putData(sessionBean: SessionBean?) {
        if (sessionBean != null) {
            //TODO ADD DATA HERE
            config = sessionBean.config
        } else {
            //TODO ADD DATA DEFAULT SINCE DE PREDETERMINATE
            config = ConfigModelApp()
        }
    }

    fun save() {
        val jsonSession = JsonUtils.createJson(this)
        mPreference.edit().putString(SessionBean::class.java.simpleName, jsonSession).apply()
    }

    fun clear() {
        //TODO CLEAN DATA HERE
        putData(null)
        save()
    }


}