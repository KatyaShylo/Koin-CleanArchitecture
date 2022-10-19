package com.example.mykinopoisk.presentation.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.domain.usecase.note.DeleteNoteUseCase
import com.example.mykinopoisk.domain.usecase.note.SubscribeUseCase
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val subscribeUseCase: SubscribeUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<Flow<List<Note>>>>(LceState.Loading)
    val lce: Flow<LceState<Flow<List<Note>>>> = _lceFlow.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeUseCase.invoke().fold(
                onSuccess = { _lceFlow.tryEmit(LceState.Success(it)) },
                onFailure = { emptyList<Note>() }
            )
        }
    }

    fun onNoteSwipe(note: Note) = viewModelScope.launch {
        deleteNoteUseCase.invoke(note)
    }
}