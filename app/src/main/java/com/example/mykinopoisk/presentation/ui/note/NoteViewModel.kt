package com.example.mykinopoisk.presentation.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteLocalRepository: NoteLocalRepository
) : ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<Flow<List<Note>>>>(LceState.Loading)
    val lce: Flow<LceState<Flow<List<Note>>>> = _lceFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val state = noteLocalRepository.subscribe().fold(
                onSuccess = { LceState.Success(it) },
                onFailure = { LceState.Fail(it) }
            )
            _lceFlow.tryEmit(state)
        }
    }

    fun onNoteSwipe(note: Note) = viewModelScope.launch {
        noteLocalRepository.deleteNote(note)
    }
}