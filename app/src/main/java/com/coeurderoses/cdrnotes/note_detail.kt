package com.coeurderoses.cdrnotes

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.sax.TextElementListener
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*
import java.util.*

//This class handle the detail of the notes
class note_detail : AppCompatActivity() {

    // i show the note received from an intent, i define the extra key in a companion object

    companion object {
        //on extra for the note information and the other the note position in the list
        val EXTRA_note = "note"
        val EXTRA_note_index="note_index"
        val REQUEST_edit_note = 1
    }
    // association of the variables which are related to the extra
    lateinit var note : Note
    var note_index : Int = -1

    lateinit var titleView : TextView
    lateinit var textNote : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar_note = note_toolbar
        setSupportActionBar(toolbar_note)
        //I set the back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //I initialize the variable from the intent called
        note = intent.getParcelableExtra(EXTRA_note)
        note_index = intent.getIntExtra(EXTRA_note_index,-1)

        //Connexion with the elements of layout note_detail
        titleView = edit_title
        textNote = edit_text

        titleView.text = note.title
        textNote.text = note.text
    }

    //I activate the customized menu by Override the onCreateOptionMenu method
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //i take the menu with inflater to take this as a Kotlin code
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }



    //i add the entry point about menu items with onOptionsMenuItemSelected method
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_save -> {
                saveNote()
                return true
            }

            R.id.action_delete -> {
                showQuestionDelete()
                return true
            }
            else ->{
                //If not i let the default behavior of the menu
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showQuestionDelete() {
        val confirmDelete = ConfirmationDelete(note.title)
        confirmDelete.listener = object : ConfirmationDelete.confirmDeleteDailogListener{
            override fun onDialogPositive() {

            }

            override fun onDialogNegative() {

            }

        }

        confirmDelete.show(supportFragmentManager,"confirmDelete")
    }

    //WHen user press the option menu save, i create an intent with the updated note
    fun saveNote(){

        note.title = titleView.text.toString()
        note.text = textNote.text.toString()
        // creation of the intent which must be send

        intent = Intent()
        intent.putExtra(EXTRA_note,note)
        intent.putExtra(EXTRA_note_index, note_index)

        //setResult Method answer to the call done in startActivityForResult, in the MainActivity ->showNoteDetail method
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
