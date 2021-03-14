package com.mx.mundet.eats

import android.app.Application
import com.mx.mundet.eats.di.ApplicationComponent
import com.mx.mundet.eats.di.DaggerApplicationComponent

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class App : Application() {

    private val component by lazy { initializeComponent() }
    //private lateinit var component : ApplicationComponent
    //private lateinit var comp : AppComponent


    private fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .appModuleCommuns(applicationContext)
                .appModuleNetwork(applicationContext)
                .build()
    }
}