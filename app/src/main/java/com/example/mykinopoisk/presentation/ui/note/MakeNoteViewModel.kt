package com.example.mykinopoisk.presentation.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.usecase.note.InsertNoteUseCase
import kotlinx.coroutines.launch

class MakeNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    suspend fun onButtonAddNote(newNote: Note) = viewModelScope.launch {
        insertNoteUseCase.invoke(newNote)
    }

}