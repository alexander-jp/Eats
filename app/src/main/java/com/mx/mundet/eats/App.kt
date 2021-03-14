package com.mx.mundet.eats

import android.app.Application
import com.mx.mundet.eats.di.ApplicationComponent

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class App : Application() {

    //private val component by lazy { ApplicationComponent  }
    private lateinit var component : ApplicationComponent
    //private lateinit var comp : AppComponent

    override fun onCreate() {
        super.onCreate()

        //component =  Dagge
    }
}