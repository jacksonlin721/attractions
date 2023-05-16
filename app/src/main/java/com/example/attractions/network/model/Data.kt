package com.example.attractions.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id"            ) var id           : Int?                = null,
    @SerializedName("name"          ) var name         : String?             = null,
    @SerializedName("name_zh"       ) var nameZh       : String?             = null,
    @SerializedName("open_status"   ) var openStatus   : Int?                = null,
    @SerializedName("introduction"  ) var introduction : String?             = null,
    @SerializedName("open_time"     ) var openTime     : String?             = null,
    @SerializedName("zipcode"       ) var zipcode      : String?             = null,
    @SerializedName("distric"       ) var distric      : String?             = null,
    @SerializedName("address"       ) var address      : String?             = null,
    @SerializedName("tel"           ) var tel          : String?             = null,
    @SerializedName("fax"           ) var fax          : String?             = null,
    @SerializedName("email"         ) var email        : String?             = null,
    @SerializedName("months"        ) var months       : String?             = null,
    @SerializedName("nlat"          ) var nlat         : Double?             = null,
    @SerializedName("elong"         ) var elong        : Double?             = null,
    @SerializedName("official_site" ) var officialSite : String?             = null,
    @SerializedName("facebook"      ) var facebook     : String?             = null,
    @SerializedName("ticket"        ) var ticket       : String?             = null,
    @SerializedName("remind"        ) var remind       : String?             = null,
    @SerializedName("staytime"      ) var staytime     : String?             = null,
    @SerializedName("modified"      ) var modified     : String?             = null,
    @SerializedName("url"           ) var url          : String?             = null,
    @SerializedName("category"      ) var category     : ArrayList<Category> = arrayListOf(),
    @SerializedName("target"        ) var target       : ArrayList<Target>   = arrayListOf(),
    @SerializedName("service"       ) var service      : ArrayList<Service>   = arrayListOf(),
    @SerializedName("friendly"      ) var friendly     : ArrayList<String>   = arrayListOf(),
    @SerializedName("images"        ) var images       : ArrayList<Images>   = arrayListOf(),
    @SerializedName("files"         ) var files        : ArrayList<String>   = arrayListOf(),
    @SerializedName("links"         ) var links        : ArrayList<Links>   = arrayListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Category)!!,
        parcel.createTypedArrayList(Target)!!,
        parcel.createTypedArrayList(Service)!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(Images)!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(Links)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(nameZh)
        parcel.writeValue(openStatus)
        parcel.writeString(introduction)
        parcel.writeString(openTime)
        parcel.writeString(zipcode)
        parcel.writeString(distric)
        parcel.writeString(address)
        parcel.writeString(tel)
        parcel.writeString(fax)
        parcel.writeString(email)
        parcel.writeString(months)
        parcel.writeValue(nlat)
        parcel.writeValue(elong)
        parcel.writeString(officialSite)
        parcel.writeString(facebook)
        parcel.writeString(ticket)
        parcel.writeString(remind)
        parcel.writeString(staytime)
        parcel.writeString(modified)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}
