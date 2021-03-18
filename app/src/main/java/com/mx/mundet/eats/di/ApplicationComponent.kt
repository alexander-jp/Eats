package com.mx.mundet.eats.di

import android.content.Context
import com.mx.mundet.eats.App
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.login.LoginComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

@Singleton
@Component(modules = [AppSubcomponents::class, ApplicationModule::class, ApplicationModuleRepository::class])
interface ApplicationComponent {

    fun inject(target: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun loginComponent(): LoginComponent.Factory

}