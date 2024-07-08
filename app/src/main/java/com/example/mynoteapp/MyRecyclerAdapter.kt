package com.example.mynoteapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mynoteapp.data.NotesDatabase
import com.example.mynoteapp.pojo.Note

class MyRecyclerAdapter(val context : Context ,var  noteList  :ArrayList<Note>)  : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>(){
    val db = NotesDatabase(context)
    class MyViewHolder(view : View) : ViewHolder(view) {

        val title_tv = view.findViewById<TextView>(R.id.list_item_title_tv)
        val content_tv = view.findViewById<TextView>(R.id.list_item_content_tv)
        val  edit_btn  = view.findViewById<ImageView>(R.id.list_item_edit)
        val delete_btn = view.findViewById<ImageView>(R.id.list_item_delete)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)

        return (MyViewHolder(itemView))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val note = noteList[position]
        holder.title_tv.text = note.title
        holder.content_tv.text = note.desc

        holder.edit_btn.setOnClickListener {
            val intent = Intent(context,UpdateNoteActivity::class.java)
            intent.putExtra("id",note.id)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.delete_btn.setOnClickListener {
           db.deleteNote(note.id)
            refreshData(db.getAllNotes())
        }

    }

    override fun getItemCount(): Int{
        return  noteList.size
    }

fun refreshData(  newNoteList: ArrayList<Note>){

       noteList =  newNoteList
    notifyDataSetChanged()
}
}