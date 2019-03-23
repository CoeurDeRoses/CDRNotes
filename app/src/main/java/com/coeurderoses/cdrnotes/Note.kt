package com.coeurderoses.cdrnotes

import android.os.Parcel
import android.os.Parcelable

//Parcelable class is used to serealize and unserealize data
//The note class describe the elements of a note
data class Note(var titre: String ="", var texte: String="", var nom_fichier: String="") : Parcelable{



    constructor(parcel: Parcel) : this(
        //The class which receive the element read them
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
        //the class which send element write them
    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}