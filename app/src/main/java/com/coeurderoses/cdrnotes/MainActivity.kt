package com.coeurderoses.cdrnotes

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var notes : MutableList<Note>
    lateinit var adapterNotes: NoteAdapter
    //to put the animation result between snackbar and floating botton
    lateinit var coordinatorLayout: CoordinatorLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar code
        val note_toolbar = note_toolbar
        // i replace the default toolbar by the customized toolbar
        setSupportActionBar(note_toolbar)

        //i create the behavior relative to the button of note creation
        note_creation.setOnClickListener(this)

        //i load the data recorded
        notes = loadNotes(this)


        //i connect the notes with the adapter
        adapterNotes = NoteAdapter(notes,this)

        //Configuration of recycler view
        val recyclerViewNotes = note_recyclerview
        // i initialize the layout to set its in vertical by default
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        //Now i connect the recycler view to the adapter
        recyclerViewNotes.adapter = adapterNotes

        coordinatorLayout = coordinator_layout
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

        //Code to handle if the action is about to delete or save data
        when(data.action){
            note_detail.ACTION_SAVE_NOTE ->{
                val note = data.getParcelableExtra<Note>(note_detail.EXTRA_note)
                saveNote(note,note_index)
            }

            note_detail.ACTION_DELETE_NOTE ->{
                deleteNote(note_index)
            }
        }


    }


    override fun onClick(view: View) {
        
        if(view.tag != null)
        {
            showNoteDetail(view.tag as Int)
        }
        else
        {
            when(view.id){
                R.id.note_creation -> createNote()
            }
        }
    }

    private fun createNote() {
        showNoteDetail(-1)
    }
    //this function is used to show a note
    fun showNoteDetail(noteIndex : Int){
        // i take the note associated to he position provided
        //the condition is set in this way to handle the exception of -1 and make the application to understand that
        // if the index is not in the array() then it mean application must create a new note
        val note = if(noteIndex<0) Note() else notes[noteIndex]
        //Here i create the intent to launch the note_detail activity
        val intent = Intent(this, note_detail::class.java)
        intent.putExtra(note_detail.EXTRA_note,note as Parcelable)
        intent.putExtra(note_detail.EXTRA_note_index, noteIndex)
        //startActivityForResult to take in back a result
        //second parameter is a request code what thing i ask
        // for example if we want read only a note or edit the note

        startActivityForResult(intent, note_detail.REQUEST_edit_note)
    }

    //Here the function to record data
    fun saveNote(note : Note, noteIndex : Int){
        persistance(this,note)
        if(noteIndex<0)
        {
            //if we create a new note we put the note in the top of the list
            notes.add(0,note)
        }
        else {//here i update the note
            notes[noteIndex] = note
        }
        //I notify the adapter of the change
        adapterNotes.notifyDataSetChanged()

    }

    fun deleteNote(note_index: Int) {
        // i put the condition to be sure to delete a note which exists
        if(note_index < 0)
            return

         val note_deleted =notes.removeAt(note_index)
        deleteNoteRecorded(this,note_deleted)
        adapterNotes.notifyDataSetChanged()

        Snackbar.make(coordinator_layout,"${note_deleted.title} supprimé", Snackbar.LENGTH_LONG).show()
    }
}
