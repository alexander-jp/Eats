package com.mx.mundet.eats.di

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
abstract class ApplicationModule {

    @Binds
    abstract fun provideContext(context: Context): Context
}
