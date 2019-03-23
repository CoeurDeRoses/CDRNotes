package com.coeurderoses.cdrnotes


import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_note.view.*

//The onLickListener object is used to make the application react from the click of user
//and notify him
//if he click a note
class NoteAdapter(val notes : List<Note>, val itemClickListener:
View.OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    //ViewHolder keep the references about graphic elements
    //references of the both textView, title and excerpt
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        //And from this we can take the title and the excerpt
        val titleView = cardView.title
        val excerptView = cardView.excerpt
    }

    //At each time a graphic element must be created, this function is called to show that element
    //an inflater object is used to convert the xml file layout in a Kotlin code that we can manipulate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent,false)

        return  ViewHolder(viewItem)
    }

    //To set the data which will be show
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.cardView.setOnClickListener(itemClickListener)
        //the tag to retrieve the not in the list
        holder.cardView.tag = position
        //update the data of ViewHolder
        holder.titleView.text = note.title
        holder.excerptView.text = note.text
    }

    /**
     * Return the number of elements in the list
     */
    override fun getItemCount(): Int {
        return notes.size
    }


}