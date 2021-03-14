package com.mx.mundet.eats.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModuleCommuns (private val application: Application) {

    @Provides
    @Singleton
    fun provideContent(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}