package com.dariwan.kupin.core.data.remote.apihelper

import io.grpc.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private const val BASE_URL = "https://4834-36-74-45-75.ngrok-free.app"

        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else{
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}