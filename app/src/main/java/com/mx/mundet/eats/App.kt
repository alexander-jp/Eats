package com.mx.mundet.eats

import android.app.Application
import com.mx.mundet.eats.di.*
import com.mx.mundet.eats.di.component.ApplicationComponent
import com.mx.mundet.eats.di.component.DaggerApplicationComponent
import com.mx.mundet.eats.di.module.*

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

open class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .appModuleCommuns(AppModuleCommuns(this))
            .applicationModuleLogin(ApplicationModuleLogin())
            .appModuleNetwork(AppModuleNetwork())
            .applicationModuleRegisterUser(ApplicationModuleRegisterUser())
            .build()
    }
//    open fun initializeComponent() : ApplicationComponent{
//        return DaggerApplicationComponent.factory().create(applicationContext)
//    }

}