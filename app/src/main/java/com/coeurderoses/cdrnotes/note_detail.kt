package com.coeurderoses.cdrnotes

import android.app.Activity

import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable

import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList


//This class handle the detail of the notes
class note_detail : AppCompatActivity(), TextToSpeech.OnInitListener {

    //Method for speaker operation
    private var tts: TextToSpeech? = null
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS)
        {
            val result =tts!!.setLanguage(Locale.FRENCH)
            if(result != TextToSpeech.LANG_MISSING_DATA || result != TextToSpeech.LANG_NOT_SUPPORTED)
            {
                button_vocal!!.isEnabled=true
            }
        }
    }

    // i show the note received from an intent, i define the extra key in a companion object

    companion object {
        //on extra for the note information and the other the note position in the list
        val EXTRA_note = "note"
        val EXTRA_note_index="note_index"
        val REQUEST_edit_note = 1

        val ACTION_SAVE_NOTE ="com.coeurderoses.cdrnotes.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE ="com.coeurdesroses.cdrnotes.actions.ACTION_DELETE_NOTE"
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

        //Implementation of button to share note
        button_share.setOnClickListener{
            Toast.makeText(applicationContext, "Ouverture des applications suggérés pour l'envoie", Toast.LENGTH_SHORT)
                .show()
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            val message_send = "Titre de la note: ${note.title} Description: ${note.text}"
            sendIntent.putExtra(Intent.EXTRA_TEXT, message_send)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        //Implementation of button to speaker note and relative code
        tts = TextToSpeech(this,this)

        button_vocal!!.isEnabled=true
        button_vocal.setOnClickListener{
            Toast.makeText(applicationContext, "Le téléphone parle", Toast.LENGTH_SHORT)
                .show()
            var textv = edit_text.text.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(textv,TextToSpeech.QUEUE_FLUSH,null, null)
            }else{
                val hash = HashMap<String,String>()
                hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_NOTIFICATION.toString())
                tts!!.speak(textv,TextToSpeech.QUEUE_FLUSH,hash)
            }
        }

        button_user_vocal.setOnClickListener{
                getInputVoice(it)
        }
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
                deleteNote()
            }

            override fun onDialogNegative() {

            }

        }

        confirmDelete.show(supportFragmentManager,"confirmDelete")
    }

    //When user press the option menu save, i create an intent with the updated note
    fun saveNote(){

        note.title = titleView.text.toString()
        note.text = textNote.text.toString()
        // creation of the intent which must be send

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_note,note as Parcelable)
        intent.putExtra(EXTRA_note_index, note_index)

        //setResult Method answer to the call done in startActivityForResult, in the MainActivity ->showNoteDetail method
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun deleteNote()
    {
        //the intent for the delete action to return to the MainActivity
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_note_index,note_index)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    //Code to stop the voice speaker operation
    public override fun onDestroy() {

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    //The vocal from the user
    public fun getInputVoice(view: View){
        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.FRENCH)

        if(intent.resolveActivity(packageManager)!=null){
        startActivityForResult(intent,10)
        }
        else
        {
            Toast.makeText(applicationContext, "Dysfonctionnement vocal", Toast.LENGTH_SHORT)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            10 ->{ if(resultCode == Activity.RESULT_OK && data!=null)
            {
                var list_string : ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                //edit_text.setText(list_string.get(0))
                edit_text.setText(edit_text.text.toString().plus(list_string.get(0)))
            }

            }

        }
    }
}
