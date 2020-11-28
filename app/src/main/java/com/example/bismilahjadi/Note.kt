package com.example.bismilahjadi

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Note(var title: String? = "",
                var txt: String? = "",
                var fileName: String? = "") : Parcelable, Serializable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(txt)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        private val serialVersionUID : Long = 4200420042
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}