package com.example.attractions.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Links() : Parcelable {
    @SerializedName("src"   ) var src     : String? = null
    @SerializedName("subject" ) var subject   : String? = null

    constructor(parcel: Parcel) : this() {
        src = parcel.readString()
        subject = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(src)
        parcel.writeString(subject)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }
}