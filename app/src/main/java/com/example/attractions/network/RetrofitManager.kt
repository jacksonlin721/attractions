package com.example.attractions.network

import com.example.attractions.network.api.AttrationListService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager {
    val baseUrl = "https://www.travel.taipei/open-api/"
    val apiTimeout = 30L

    val attrationListAPI: AttrationListService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(apiTimeout, TimeUnit.SECONDS)
            .connectTimeout(apiTimeout, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("accept", "application/json")
                    .method(original.method(), original.body())
                    .build()

                chain.proceed(request)
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        attrationListAPI = retrofit.create(AttrationListService::class.java)
    }

    companion object {
        private var mInstance: RetrofitManager? = null

        fun getInstance(): RetrofitManager {
            if (mInstance == null)
                mInstance = RetrofitManager()
            return mInstance!!
        }
    }
}