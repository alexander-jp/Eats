package com.mx.mundet.eats.di.component

import com.mx.mundet.eats.di.module.*
import com.mx.mundet.eats.ui.mvp.detailUser.ActivityDetailUser
import com.mx.mundet.eats.ui.mvp.home.FragmentHome
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserActivity
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

@Singleton
@Component(modules = [ApplicationModule::class,
                      AppModuleCommuns::class,
                      AppModuleNetwork::class,
                      ApplicationModuleHome::class,
                      ApplicationModulePresenter::class,
                      ApplicationModuleRepository::class])

interface ApplicationComponent {

    //TODO Activity injection

    fun inject(target: LoginActivity)

    fun inject(target: RegisterUserActivity)

    //TODO Fragments injection

    fun inject(target : FragmentHome)

    fun inject (target : ActivityDetailUser)
}