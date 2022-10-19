package com.example.mykinopoisk.domain.usecase.note

import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository

class DeleteNoteUseCase(
    private val repository: NoteLocalRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}