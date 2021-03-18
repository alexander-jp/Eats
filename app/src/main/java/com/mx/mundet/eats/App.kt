package com.mx.mundet.eats

import android.app.Application
import com.mx.mundet.eats.di.*
import dagger.internal.DaggerCollections

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

open class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }


    open fun initializeComponent() : ApplicationComponent{
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
//    override fun onCreate() {
//        super.onCreate()
//        component = DaggerApplicationComponent.builder()
//            .applicationModule(ApplicationModule(this))
//            .appModuleCommuns(AppModuleCommuns(this))
//            .applicationModuleRepository(ApplicationModuleRepository())
//            .build()
//    }

}