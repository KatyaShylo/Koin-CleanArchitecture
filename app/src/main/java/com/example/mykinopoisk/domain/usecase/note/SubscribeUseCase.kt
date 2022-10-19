package com.example.mykinopoisk.domain.usecase.note

import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import kotlinx.coroutines.flow.Flow

class SubscribeUseCase(
    private val repository: NoteLocalRepository
) {
    suspend operator fun invoke(): Result<Flow<List<Note>>> {
        return repository.subscribe()
    }
}