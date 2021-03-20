package com.mx.mundet.eats.di.module

import android.content.Context
import com.mx.mundet.eats.bd.AppRoomDatabase
import com.mx.mundet.eats.domain.api.PersonasSource
import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.domain.repository.LoginRepositoryImpl
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import com.mx.mundet.eats.ui.mvp.login.LoginPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModuleLogin {

//    var view : LoginActivity?=null

//    @Singleton
//    @Provides
//    fun provideLoginView() :LoginActivity{
//        this.view = LoginActivity()
//        return LoginActivity()
//    }

    @Singleton
    @Provides
    fun provideLoginPresenter(repo : LoginRepository) : LoginContract.Presenter{
        return LoginPresenter(repo)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(source: PersonasSource, database: AppRoomDatabase, context: Context): LoginRepository {
        return LoginRepositoryImpl(source, database, context)
    }
}