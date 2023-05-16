package com.example.attractions.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Target() : Parcelable {
    @SerializedName("id"   ) var id     : String? = null
    @SerializedName("name" ) var name   : String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Target> {
        override fun createFromParcel(parcel: Parcel): Target {
            return Target(parcel)
        }

        override fun newArray(size: Int): Array<Target?> {
            return arrayOfNulls(size)
        }
    }
}