package com.coeurderoses.cdrnotes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var notes : MutableList<Note>
    lateinit var adapterNotes: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes = mutableListOf()
        notes.add(Note("1","un"))
        notes.add(Note("2","deux"))

        //i connect the notes with the adapter
        adapterNotes = NoteAdapter(notes,this)

        //Configuration of recycler view
        val recyclerViewNotes = note_recyclerview
        // i initialize the layout to set its in vertical by default
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        //Now i connect the recycler view to the adapter
        recyclerViewNotes.adapter = adapterNotes
    }

    //this function is used to show a note
    fun showNoteDetail(noteIndex : Int){
        // i take the note associated to he position provided
        val note = notes[noteIndex]
        //Here i create the intent to launch the note_detail activity
        val intent = Intent(this, note_detail::class.java)
        intent.putExtra(note_detail.EXTRA_note,note)
        intent.putExtra(note_detail.EXTRA_note_index, noteIndex)
        startActivity(intent)
    }

    override fun onClick(view: View) {
        
        if(view.tag != null)
        {
            showNoteDetail(view.tag as Int)
        }
    }
}
