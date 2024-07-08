package com.example.mynoteapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynoteapp.data.NotesDatabase
import com.example.mynoteapp.databinding.ActivityUpdateNoteBinding
import com.example.mynoteapp.pojo.Note

class UpdateNoteActivity : AppCompatActivity() {
    lateinit var binding : ActivityUpdateNoteBinding
    lateinit var db : NotesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra("id",-1)


        db = NotesDatabase(this)

     if(id != -1 ){
    val note = db.getNoteById(id)
    binding.updateTitleEt.setText(note.title)
    binding.updateContentEt.setText(note.desc)
}else{
    
    Toast.makeText(this," error  ",Toast.LENGTH_SHORT).show()
     }




        binding.updateBtn.setOnClickListener {
            val new_title = binding.updateTitleEt.text.toString()
            val new_desc = binding.updateContentEt.text.toString()
            db.updateNote(Note(id,new_title,new_desc))
            finish()
            Toast.makeText(this," Successfully Updated ",Toast.LENGTH_SHORT).show()

        }

    }
}