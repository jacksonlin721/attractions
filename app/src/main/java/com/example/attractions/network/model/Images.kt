package com.example.attractions.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Images() : Parcelable {
    @SerializedName("src"     ) var src     : String? = null
    @SerializedName("subject" ) var subject : String? = null
    @SerializedName("ext"     ) var ext     : String? = null

    constructor(parcel: Parcel) : this() {
        src = parcel.readString()
        subject = parcel.readString()
        ext = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(src)
        parcel.writeString(subject)
        parcel.writeString(ext)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Images> {
        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }
}
