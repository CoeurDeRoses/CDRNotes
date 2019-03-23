package com.coeurderoses.cdrnotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    override fun onClick(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
