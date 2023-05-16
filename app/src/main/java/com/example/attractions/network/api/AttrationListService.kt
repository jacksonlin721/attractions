package com.example.attractions.network.api

import com.example.attractions.network.model.AttractionList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AttrationListService {

    @GET("{lang}/Attractions/All?")
    fun getAttractionList(@Path("lang") language: String, @Query("page") p: String): Call<AttractionList>

    @GET("{lang}/Attractions/All?")
    suspend fun getAttractionListSuspend(@Path("lang") language: String, @Query("page") p: String): AttractionList

    @GET
    fun getAttractionListURL(@Url url: String, @Query("page") p: String): Call<AttractionList>
}