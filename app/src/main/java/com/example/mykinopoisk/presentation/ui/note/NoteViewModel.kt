package com.example.mykinopoisk.presentation.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import com.example.mykinopoisk.presentation.model.LceState
import com.example.mykinopoisk.presentation.ui.movie.MoviesViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
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

    fun onChangeData(note: Note) = viewModelScope.launch{
        noteLocalRepository.update(note)
    }
}