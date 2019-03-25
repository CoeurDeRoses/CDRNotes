package com.coeurderoses.cdrnotes

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

//Parcelable class is used to serealize and unserealize data
//The note class describe the elements of a note
data class Note(var title: String ="", var text: String="", var nom_fichier: String="") : Parcelable, Serializable{



    constructor(parcel: Parcel) : this(
        //The class which receive the element read them
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
        //the class which send element write them
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(nom_fichier)
    }

    override fun describeContents(): Int {
        return 0

    }

    companion object CREATOR : Parcelable.Creator<Note> {

        private val serialVersionUid: Long = 123456789
        //this ID is unique and make understand to the JVM the versions of the class note
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}