package com.mx.mundet.eats.di.module

import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserContract
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

@Module
class ApplicationModuleRegisterUser {

    @Singleton
    @Provides
    fun provideRegisterUserPresenter (repo : LoginRepository) : RegisterUserContract.Presenter {
        return RegisterUserPresenter(repo)
    }


}