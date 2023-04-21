package com.dvargas.adaschool.cornershop.dataLayer.network.module

import com.dvargas.adaschool.cornershop.domain.services.AuthService
import com.dvargas.adaschool.cornershop.domain.services.HealthService
import com.dvargas.adaschool.cornershop.domain.services.UserService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object NetworkServiceModule {


    @Provides
    fun providesHealthService(retrofit: Retrofit): HealthService {
        return retrofit.create(HealthService::class.java)
    }

    @Provides
    fun providesAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun providesRetrofit(): Retrofit {
        val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor()
        loggingInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        val okHttpClient = okhttp3.OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .writeTimeout(0, java.util.concurrent.TimeUnit.MILLISECONDS)
            .readTimeout(2, java.util.concurrent.TimeUnit.MINUTES)
            .connectTimeout(1, java.util.concurrent.TimeUnit.MINUTES).build()

        val gson = com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create()

        return Retrofit.Builder().baseUrl("https://products-api.up.railway.app/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

}