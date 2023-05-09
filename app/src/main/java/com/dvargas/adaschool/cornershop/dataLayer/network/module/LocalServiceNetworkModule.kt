package com.dvargas.adaschool.cornershop.dataLayer.network.module

import com.dvargas.adaschool.cornershop.domain.services.local.LoginService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object LocalServiceNetworkModule {

    @Provides
    @Named("local")
    fun providesLoginService(@Named("local") retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Named("local")
    fun providesRetrofit(): Retrofit {
        val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor()
        loggingInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        val okHttpClient = okhttp3.OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .writeTimeout(0, java.util.concurrent.TimeUnit.MILLISECONDS)
            .readTimeout(2, java.util.concurrent.TimeUnit.MINUTES)
            .connectTimeout(1, java.util.concurrent.TimeUnit.MINUTES).build()

        val gson = com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create()

        return Retrofit.Builder().baseUrl("http://10.0.2.2:8080/concessionaire/user/")
            .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }
}