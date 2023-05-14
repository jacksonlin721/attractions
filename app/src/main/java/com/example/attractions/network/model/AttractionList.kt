package com.example.attractions.network.model

import com.google.gson.annotations.SerializedName

data class AttractionList(
    @SerializedName("total" ) var total : Int?            = null,
    @SerializedName("data"  ) var data  : ArrayList<Data> = arrayListOf()
)
