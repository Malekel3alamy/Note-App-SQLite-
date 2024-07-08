package com.example.mynoteapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynoteapp.data.NotesDatabase
import com.example.mynoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    lateinit var myadapter  : MyRecyclerAdapter
    var db  = NotesDatabase(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatBtnAdd.setOnClickListener {

            val intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }

        setAdapter()

    }

    fun setAdapter(){
        myadapter = MyRecyclerAdapter(applicationContext,db.getAllNotes())
        binding.myRecycler.layoutManager = LinearLayoutManager(this)
        binding.myRecycler.adapter = myadapter

    }

    override fun onResume() {
        super.onResume()

        myadapter.refreshData(db.getAllNotes())
    }
}