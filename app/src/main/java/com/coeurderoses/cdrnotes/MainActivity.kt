package com.coeurderoses.cdrnotes

import android.app.Activity
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

        //toolbar code
        val note_toolbar = note_toolbar
        // i replace the default toolbar by the customized toolbar
        setSupportActionBar(note_toolbar)

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
        //startActivityForResult to take in back a result
        //second parameter is a request code what thing i ask
        // for example if we want read only a note or edit the note

        startActivityForResult(intent, note_detail.REQUEST_edit_note)
    }

    //take result of the activity asked in showNoteDetail function
    // by note_detail_activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //if the user open a note and press back after then nothing have do be done
        if(resultCode!= Activity.RESULT_OK || data==null)
            return

        //if he modify data
        when(requestCode){
            note_detail.REQUEST_edit_note -> EditNoteResult(data)
        }
    }

    fun EditNoteResult(data: Intent) {
        val note_index = data.getIntExtra(note_detail.EXTRA_note_index, -1)

        val note = data.getParcelableExtra<Note>(note_detail.EXTRA_note)

        saveNote(note,note_index)
    }

    //Here the function to record data
    fun saveNote(note : Note, noteIndex : Int){
        //here i update the note
        notes[noteIndex] = note
        //I notify the adapter of the change
        adapterNotes.notifyDataSetChanged()

    }

    override fun onClick(view: View) {
        
        if(view.tag != null)
        {
            showNoteDetail(view.tag as Int)
        }
    }
}
