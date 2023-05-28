package com.example.attractions.api

import android.util.Log
import com.example.attractions.network.*
import com.example.attractions.network.model.AttractionList
import org.w3c.dom.Attr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttractionListAPI {

    fun getAttractionList(language: String, page: String): Call<AttractionList> {
        return RetrofitManager.getInstance().attrationListAPI.getAttractionList(
            language, page
        )
    }

}