package com.coeurderoses.cdrnotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.sax.TextElementListener
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*

//This class handle the detail of the notes
class note_detail : AppCompatActivity() {

    // i show the note received from an intent, i define the extra key in a companion object

    companion object {
        //on extra for the note information and the other the note position in the list
        val EXTRA_note = "note"
        val EXTRA_note_index="note_index"
    }
    // association of the variables which are related to the extra
    lateinit var note : Note
    var note_index : Int = -1

    lateinit var titleView : TextView
    lateinit var textNote : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        //I initialize the variable from the intent called
        note = intent.getParcelableExtra(EXTRA_note)
        note_index = intent.getIntExtra(EXTRA_note_index,-1)

        //Connexion with the elements of layout note_detail
        titleView = edit_title
        textNote = edit_text

        titleView.text = note.title
        textNote.text = note.text
    }
}
