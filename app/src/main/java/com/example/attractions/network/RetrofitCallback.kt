package com.example.attractions.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitCallback<T>: Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.e("RetrofitCallback", "Retrofit request url ${call.request().url()}")
        val response: String = Gson().toJson(response.body())
        Log.e("RetrofitCallback", "Retrofit response $response")
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }
}