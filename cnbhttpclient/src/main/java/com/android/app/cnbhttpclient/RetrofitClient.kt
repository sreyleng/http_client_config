package com.android.app.cnbhttpclient

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private fun provideOkHttpClient(): OkHttpClient {
        val intercepter = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {

            // Add an interceptor to include headers
            this.addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithHeaders = originalRequest.newBuilder()
                    .addHeader("X-Api-Key", "d809d6a547734a67af23365ce5bc8c02")
                    // .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(requestWithHeaders)
            }
                .addInterceptor(intercepter)

            // Timeout settings
            this.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    /**Provides a Gson instance for JSON serialization/deserialization*/
    private fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    // Function to create a Retrofit instance with any ApiService interface
    fun <T> createService(url: String, serviceClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()

        return retrofit.create(serviceClass)
    }


}
