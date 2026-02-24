package com.example.happydocx.Utils

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance{

    private val BASE_URL = "https://api.happydocx.com/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        //Connection Pool is just a "don't hang up, put it on hold" strategy — so next time you need the server, you skip all the introductions and get straight to business.
        .connectionPool(ConnectionPool( // connection pool is like not cut the call put it in hold so that you not again need to call the same api for result
            maxIdleConnections = 5, // keep 5 lines on hold
            keepAliveDuration = 5, // after timeUnit hang the call
            timeUnit = TimeUnit.MINUTES // after 5 minutes
        ))
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // after using this no need to add @Serializable to data classes
            .build()
    }
}