package com.example.mynoteapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynoteapp.data.NotesDatabase
import com.example.mynoteapp.databinding.ActivityAddNoteBinding
import com.example.mynoteapp.pojo.Note

class AddNoteActivity : AppCompatActivity() {
    lateinit var  binding : ActivityAddNoteBinding
    lateinit var db:NotesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabase(this)

        binding.addNoteButton.setOnClickListener {

            val title = binding.titleEditText.text.toString()
            val desc  = binding.contentEditText.text.toString()
if(title.isNotEmpty() && desc.isNotEmpty()){
    val note = Note(0,title,desc)
    db.insertNote(note)
    finish()
    Toast.makeText(this," Note Saved " , Toast.LENGTH_SHORT).show()
}else{

    Toast.makeText(this," Empty Note  " ,Toast.LENGTH_SHORT).show()

}



        }

    }
}