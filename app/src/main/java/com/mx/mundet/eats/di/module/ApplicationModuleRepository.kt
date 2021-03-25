package com.mx.mundet.eats.di.module

import android.content.Context
import com.mx.mundet.eats.bd.AppRoomDatabase
import com.mx.mundet.eats.domain.api.PersonasSource
import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.domain.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexander Juárez with Date 24/03/2021
 * @author Alexander Juárez
 */

@Module
class ApplicationModuleRepository {

    //TODO for UserRepository
    @Singleton
    @Provides
    fun provideUserRepository(source: PersonasSource, database: AppRoomDatabase, context: Context): UserRepository {
        return UserRepositoryImpl(source, database, context)
    }
}