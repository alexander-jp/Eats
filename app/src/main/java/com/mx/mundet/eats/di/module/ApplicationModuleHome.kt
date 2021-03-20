package com.mx.mundet.eats.di.module

import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.ui.mvp.home.FragmentHome
import com.mx.mundet.eats.ui.mvp.home.HomeContract
import com.mx.mundet.eats.ui.mvp.home.HomePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

@Module
class ApplicationModuleHome {
    //TODO support all fragment the App

    @Singleton
    @Provides
    fun provideHomeView() : HomeContract.View{

        return FragmentHome()
    }

    @Singleton
    @Provides
    fun provideHomePresenter(repo: LoginRepository): HomeContract.Presenter {
        return HomePresenter(repo)
    }
}