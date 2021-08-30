package com.app.codehero.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
FELIPE
 */
object WebApiAccess {

    const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS).build()

    val marvelApi : MarvelApiClient by lazy {
        val retrofitBuilder =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return@lazy retrofitBuilder.create(MarvelApiClient::class.java)
    }
}