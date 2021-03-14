package com.mx.mundet.eats.di

import com.mx.mundet.eats.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

@Component(modules = [AppModuleNetwork::class, AppModuleCommuns::class])
interface ApplicationComponent {

    fun inject(app : MainActivity)
}