package com.example.attractions.network.model

import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id"   ) var id   : Int?    = null
    @SerializedName("name" ) var name : String? = null
}
