package com.mx.mundet.eats.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 17/03/2021
 * @author Alexander Juárez
 */

@Module
class ApplicationModule(private val application: Application) {


    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}
