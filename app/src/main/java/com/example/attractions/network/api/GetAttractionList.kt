package com.example.attractions.network.api

import android.util.Log
import com.example.attractions.network.*
import com.example.attractions.network.model.AttractionList
import org.w3c.dom.Attr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAttractionList {
    companion object {

        suspend fun getAttractionListSuspend(language: String, page: String): AttractionList {
            return RetrofitManager.getInstance().attrationListAPI.getAttractionListSuspend(
                language, page
            )
        }

        fun getAttractionList(language: String, page: String, future: RequestFuture<AttractionList>) {
//            val url = RetrofitManager.getInstance().baseUrl + "zh-tw/Attractions/All?"
//            RetrofitManager.getInstance().attrationListAPI.getAttractionListURL(
            RetrofitManager.getInstance().attrationListAPI.getAttractionList(
                language, page
            ).enqueue(object : RetrofitCallback<AttractionList>() {
                override fun onResponse(
                    call: Call<AttractionList>,
                    response: Response<AttractionList>
                ) {
                    super.onResponse(call, response)
//                    Log.e("GetAttractionList", "response= ${response.body()}")
                    future.onResponse(response.body())
                }

                override fun onFailure(call: Call<AttractionList>, t: Throwable) {
                    super.onFailure(call, t)
                    future.onErrorResponse(call, t)
                }
            })
        }

    }
}