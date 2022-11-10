package com.example.mykinopoisk.data.database

import androidx.room.*
import com.example.mykinopoisk.data.model.note.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
internal interface  NoteDao {

    @Query("SELECT * FROM noteentity")
    suspend fun getAllNote(): List<NoteEntity>

    @Insert
    suspend fun insertNote(notes: NoteEntity)

    @Query("SELECT * FROM noteentity")
    fun observeNote(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity")
    fun subscribe(): Flow<List<NoteEntity>>

    @Update
    suspend fun update(note: NoteEntity)

}