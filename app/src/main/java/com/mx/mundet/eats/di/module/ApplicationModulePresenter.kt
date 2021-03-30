package com.mx.mundet.eats.di.module

import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.ui.mvp.detailUser.DetailUserContract
import com.mx.mundet.eats.ui.mvp.detailUser.DetailUserPresenter
import com.mx.mundet.eats.ui.mvp.home.HomeContract
import com.mx.mundet.eats.ui.mvp.home.HomePresenter
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import com.mx.mundet.eats.ui.mvp.login.LoginPresenter
import com.mx.mundet.eats.ui.mvp.register.RegisterContract
import com.mx.mundet.eats.ui.mvp.register.RegisterPresenter
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserContract
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 24/03/2021
 * @author Alexander Juárez
 */
@Module
class ApplicationModulePresenter {

    //TODO for LoginPresenter
    @Singleton
    @Provides
    fun provideLoginPresenter(repo : UserRepository) : LoginContract.Presenter{
        return LoginPresenter(repo)
    }

    //TODO for HomePresenter

    @Singleton
    @Provides
    fun provideHomePresenter(repo: UserRepository): HomeContract.Presenter {
        return HomePresenter(repo)
    }

    //TODO for RegisterUserPresenter
    @Singleton
    @Provides
    fun provideRegisterUserPresenter (repo : UserRepository) : RegisterUserContract.Presenter {
        return RegisterUserPresenter(repo)
    }


    //TODO for DetailUserPresenter
    @Singleton
    @Provides
    fun providePresenterDetailUser(repo : UserRepository) : DetailUserContract.Presenter{
        return DetailUserPresenter(repo)
    }

    //TODO for RegisterPresenter
    @Singleton
    @Provides
    fun providePresenterRegister(repo : UserRepository) : RegisterContract.Presenter{
        return RegisterPresenter(repo)
    }

}