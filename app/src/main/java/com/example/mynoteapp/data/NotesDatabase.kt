package com.example.mynoteapp.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mynoteapp.pojo.Note

class NotesDatabase (val context : Context) : SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {

    companion object{

        private const val DB_NAME = "NotesDatabase"
        private const val TABLE_NAME = "NotesTable"
        private const val COLUMNS_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "desc"
        private const val DB_VERSION = 2


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(" CREATE TABLE $TABLE_NAME($COLUMNS_ID INTEGER  PRIMARY KEY AUTOINCREMENT ," +
                " $COLUMN_TITLE TEXT not null  , $COLUMN_DESC  TEXT not null ) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(" DROP TABLE  IF EXISTS $TABLE_NAME ")
        onCreate(db)
    }

    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_DESC,note.desc)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }


    fun getAllNotes() : ArrayList<Note>{

        val db = readableDatabase
        val noteList = ArrayList<Note>()
        val query = " SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        if(cursor != null && cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMNS_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))

                noteList.add(Note(id,title,desc))

            }while (cursor.moveToNext())
        }

              cursor.close()
              db.close()
        return  noteList
    }
    fun updateNote(note: Note) : Boolean {

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_DESC,note.desc)
        }
        val whereClause = "$COLUMNS_ID = ? "
        val whereArgs = arrayOf(note.id.toString())

        val result = db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
return(result != -1)
    }

    fun deleteNote(id: Int) : Boolean{

        val db = writableDatabase
        val whereClause = " $COLUMNS_ID =? "
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(TABLE_NAME,whereClause,whereArgs)

        db.close()
        return(result != -1 )
    }

    fun getNoteById(noteId : Int) : Note{
        var note :Note? = null

        val db = readableDatabase
        val query = " SELECT * FROM $TABLE_NAME WHERE $COLUMNS_ID = ? "
        val cursor = db.rawQuery(query, arrayOf(noteId.toString()))
        if(cursor != null && cursor.moveToFirst()){
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val  desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))

             note = Note(noteId,title,desc)
            cursor.close()
            db.close()

        }
        return note!!
    }



}