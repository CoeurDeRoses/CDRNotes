package com.coeurderoses.cdrnotes
//this file handle the operation about data record
import android.content.Context
import android.text.TextUtils
import java.io.ObjectOutputStream
import java.util.*

fun persistance(context: Context, note: Note){
    // if there not filename it's a new note we have to record
    if(TextUtils.isEmpty(note.nom_fichier))
    {
        //Random unique ID generation of a name from class UUID
        note.nom_fichier = UUID.randomUUID().toString()+".note"
    }

    //Serealization


    //Context is used to access to the file recorded in a directory of the application
    val fileOutput = context.openFileOutput(note.nom_fichier,Context.MODE_PRIVATE)

    //Open write stream
    val outputStream = ObjectOutputStream(fileOutput)

    //Serealizable code convert automatically the variable member of class note and write them inside the file
    outputStream.writeObject(note)
    //once we have done we close the stream
    outputStream.close()
}