package com.example.attractions.network.model

import com.google.gson.annotations.SerializedName

class Target {
    @SerializedName("id"   ) var id     : String? = null
    @SerializedName("name" ) var name   : String? = null
}