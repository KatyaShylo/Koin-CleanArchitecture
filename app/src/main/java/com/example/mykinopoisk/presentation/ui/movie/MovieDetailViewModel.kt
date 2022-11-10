package com.example.mykinopoisk.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.repository.movie.MovieRepository
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val movieId: Long
) : ViewModel() {

    val movieDetailFlow = flow {
        delay(2000)
        val state = repository.getMovieDetail(
            TOKEN,
            FIELD_ID,
            movieId.toString()
        ).fold(
            onSuccess = { LceState.Success(it) },
            onFailure = { LceState.Fail(it) }
        )
        emit(state)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        LceState.Loading
    )

    fun onSend() {
        movieDetailFlow
    }

    companion object {
        private const val TOKEN = "PR0J8XR-XTCM5Y8-P3RHWPK-WYEKR6W"
        private const val FIELD_ID = "id"
    }
}