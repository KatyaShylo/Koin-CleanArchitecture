package com.example.mykinopoisk.data.repository.note

import com.example.mykinopoisk.data.database.NoteDao
import com.example.mykinopoisk.data.mapper.toDomainModel
import com.example.mykinopoisk.data.mapper.toEntityModel
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NoteLocalRepositoryImpl(
    private val noteDao: NoteDao
) : NoteLocalRepository {

    override suspend fun getAllNote(): List<Note> {
        return noteDao.getAllNote().map { it.toDomainModel() }
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toEntityModel())
    }

    override fun observeNote(): Flow<List<Note>> {
        return noteDao.observeNote().map {
            it.map { noteEntity ->
                noteEntity.toDomainModel()
            }
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note.toEntityModel())
    }

    override suspend fun subscribe(): Result<Flow<List<Note>>> =
        runCatching {
            noteDao.subscribe().map {
                it.map { noteEntity ->
                    noteEntity.toDomainModel()
                }
            }
        }
}