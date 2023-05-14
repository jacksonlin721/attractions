package com.example.attractions.network.model

import com.google.gson.annotations.SerializedName

class Images {
    @SerializedName("src"     ) var src     : String? = null
    @SerializedName("subject" ) var subject : String? = null
    @SerializedName("ext"     ) var ext     : String? = null
}
