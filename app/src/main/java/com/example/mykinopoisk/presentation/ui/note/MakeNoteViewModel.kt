package com.example.mykinopoisk.presentation.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import kotlinx.coroutines.launch

class MakeNoteViewModel(
    private val noteLocalRepository: NoteLocalRepository
) : ViewModel() {

    suspend fun onButtonAddNote(newNote: Note) = viewModelScope.launch {
        noteLocalRepository.insertNote(newNote)
    }
}