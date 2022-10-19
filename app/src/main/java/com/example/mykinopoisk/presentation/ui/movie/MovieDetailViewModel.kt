package com.example.mykinopoisk.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.usecase.movie.GetMovieDetailUseCase
import com.example.mykinopoisk.presentation.constants.Constants
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val movieId: Long
) : ViewModel() {

    val movieDetailFlow = flow {
        delay(2000)
        val state = getMovieDetailUseCase.invoke(
            Constants.TOKEN,
            Constants.FIELD_ID,
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
}