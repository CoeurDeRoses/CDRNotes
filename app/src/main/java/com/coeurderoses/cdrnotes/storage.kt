package com.coeurderoses.cdrnotes
//this file handle the operation about data record
import android.content.Context
import android.text.TextUtils
import java.io.ObjectInputStream
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

//this function open a file note from parameter and return a note
private fun loadNote(context: Context, fileName: String) : Note{
    //Context is used to access to the file recorded in a directory of the application
    val fileInput = context.openFileInput(fileName)

    //Open read stream
    val inputStream = ObjectInputStream(fileInput)

    val note = inputStream.readObject() as Note
    //Once we have done we close input stream
    inputStream.close()

    return note
}

//this function check all the files to generate the list of note
fun loadNotes(context: Context) : MutableList<Note>{ //MutableList used to allow the edition of note
    val notes = mutableListOf<Note>()

    //i take the directory of application where all notes are stored
    val notesDir = context.filesDir

    //at each turn i add the note recorded to the list
    for (fileName in notesDir.list())
    {
        val note = loadNote(context, fileName)

        notes.add(note)
    }

    return notes

}

fun deleteNoteRecorded(context: Context, note: Note){
    context.deleteFile(note.nom_fichier)
}