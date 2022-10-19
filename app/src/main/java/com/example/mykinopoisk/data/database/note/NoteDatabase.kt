package com.example.mykinopoisk.data.database.note

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mykinopoisk.data.model.note.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}