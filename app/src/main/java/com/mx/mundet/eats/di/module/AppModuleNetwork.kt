package com.mx.mundet.eats.di.module

import com.mx.mundet.eats.BuildConfig
import com.mx.mundet.eats.domain.api.PersonasSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModuleNetwork {


//    @Provides
//    @Singleton
//    fun provideOkHttpCache(application: Application) : Cache{
//        val cacheSize = 20 * 1024 * 2024 //10 MB
//        return Cache(application.cacheDir, cacheSize.toLong())
//    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            //.cache(cache)
            .addInterceptor(interceptorLogger)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(urlBase: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(urlBase)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(): PersonasSource {
        return provideRetrofit(BuildConfig.BASE_URL, provideOkHttpClient()).create(PersonasSource::class.java)
    }

//    @Singleton
//    @Provides
//    inline fun <reified T> provideApiServiceSource(): T {
//        return provideRetrofit("", provideOkHttpClient()).create(T::class.java)
//    }

}
