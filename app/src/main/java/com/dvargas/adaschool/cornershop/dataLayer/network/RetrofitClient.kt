package com.dvargas.adaschool.cornershop.dataLayer.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    fun <T> create(service: Class<T>, url: String): T {
        val retrofit = Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create(service)
    }
}