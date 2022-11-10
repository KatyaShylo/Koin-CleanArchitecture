package com.example.mykinopoisk.domain.repository.note

import com.example.mykinopoisk.domain.model.note.Note
import kotlinx.coroutines.flow.Flow

interface NoteLocalRepository {

    suspend fun getAllNote(): List<Note>

    suspend fun insertNote(note: Note)
    suspend fun update(note: Note)

    fun observeNote(): Flow<List<Note>>

    suspend fun deleteNote(note: Note)

    suspend fun subscribe(): Result<Flow<List<Note>>>
}