package com.example.attractions.network.api

import android.util.Log
import com.example.attractions.network.RequestFuture
import com.example.attractions.network.RetrofitCallback
import com.example.attractions.network.RetrofitManager
import com.example.attractions.network.model.AttractionList
import org.w3c.dom.Attr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAttractionList {
    companion object {

        fun getAttractionList(language: String, page: String, future: RequestFuture<AttractionList>) {
//            val url = RetrofitManager.getInstance().baseUrl + "zh-tw/Attractions/All?"
//            RetrofitManager.getInstance().attrationListAPI.getAttractionListURL(
            RetrofitManager.getInstance().attrationListAPI.getAttractionList(
                language, page
            ).enqueue(object : Callback<AttractionList> {
                override fun onResponse(
                    call: Call<AttractionList>,
                    response: Response<AttractionList>
                ) {
//                    Log.e("GetAttractionList", "response= ${response.body()}")
                    future.onResponse(response.body()!!)
                }

                override fun onFailure(call: Call<AttractionList>, t: Throwable) {
                    future.onErrorResponse(call, t)
                }

            })

            /*RetrofitManager.getInstance().attrationListAPI.getAttractionList(
                language, page
            ).enqueue(object : RetrofitCallback<AttractionList>() {
                override fun onResponse(
                    call: Call<AttractionList>,
                    response: Response<AttractionList>
                ) {
                    super.onResponse(call, response)
                    Log.e("GetAttractionList", "response= ${response.body()}")
                    future.onResponse(response.body()!!)
                }

                override fun onFailure(call: Call<AttractionList>, t: Throwable) {
                    super.onFailure(call, t)
                    future.onErrorResponse(call, t)
                }
            })*/
        }
    }
}