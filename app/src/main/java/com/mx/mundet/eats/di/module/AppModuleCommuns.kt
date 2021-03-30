package com.mx.mundet.eats.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mx.mundet.eats.bd.AppRoomDatabase
import com.mx.mundet.eats.ui.base.SessionBean
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModuleCommuns (private val app: Application) {

    @Singleton
    @Provides
    fun provideApplication () : Application{
        return app
    }


    @Singleton
    @Provides
    fun provideDataBase(app : Application) : AppRoomDatabase{
        return AppRoomDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideSessionBean() : SessionBean {
        return SessionBean(provideSharedPref(app))
    }

    @Singleton
    @Provides
    fun provideSharedPref(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}