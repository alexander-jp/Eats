package com.mx.mundet.eats.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModuleNetwork (private val application: Application) {

    @Provides
    @Singleton
    fun provideContent(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application) : Cache{
        val cacheSize = 20 * 1024 * 2024 //10 MB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache : Cache): OkHttpClient{
        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(interceptorLogger)
                .build()
    }

    @Provides
    @Singleton
    private inline fun <reified T> provideRetrofitSource(urlBase:String,okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(urlBase)
                .build()
        return retrofit.create(T::class.java)
    }


}
