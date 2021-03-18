package com.mx.mundet.eats.di

import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.domain.repository.LoginRepositoryImpl
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import com.mx.mundet.eats.ui.mvp.login.LoginPresenter
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.login.LoginContractPrueba
import dagger.Module
import dagger.Provides

@Module
class ApplicationModuleRepository {


    var presenter : LoginContract.Presenter?=null
//
//
//    @Provides
//    fun provideLoginView(): LoginActivity{
//        this.presenter = LoginPresenter(LoginRepositoryImpl())
//        return LoginActivity()
//    }

//    @Provides
//    fun provideRepository(source: PersonasSource): LoginRepository {
//        return LoginRepositoryImpl(source)
//    }

    @Provides
    fun provideLoginPresenter(repo : LoginRepository) : LoginContractPrueba.Presenter{
        return LoginPresenter(repo)
    }

    @Provides
    fun provideLoginRepositorio() : LoginRepository{
        return LoginRepositoryImpl()
    }
}