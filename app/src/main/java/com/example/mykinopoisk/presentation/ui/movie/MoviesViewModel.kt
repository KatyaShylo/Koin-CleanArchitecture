package com.example.mykinopoisk.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.usecase.favorite.InsertFavoriteUseCase
import com.example.mykinopoisk.domain.usecase.movie.GetAllMoviesFromDatabaseUseCase
import com.example.mykinopoisk.domain.usecase.movie.GetAllMoviesUseCase
import com.example.mykinopoisk.domain.usecase.movie.InsertMoviesInDatabaseUseCase
import com.example.mykinopoisk.presentation.constants.Constants
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val getAllMoviesFromDatabaseUseCase: GetAllMoviesFromDatabaseUseCase,
    private val insertMoviesInDatabaseUseCase: InsertMoviesInDatabaseUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val dataFlow = queryFlow.combine(
        refreshFlow.map {
            delay(2000)
            runCatching {
                getAllMoviesUseCase.invoke(
                    Constants.TOKEN,
                    Constants.LIMIT,
                    Constants.FIELD_YEAR,
                    Constants.SEARCH_YEAR,
                    Constants.FIELD_RATING,
                    Constants.SEARCH_RATING
                )
            }.onSuccess { LceState.Success(it) }
                .onFailure { LceState.Fail(it) }
        }
    ) { query, result ->
        result.map {
            it.map { it.listMovie.filter { it.name.contains(query, ignoreCase = true) } }.fold(
                onSuccess = {
                    insertMoviesInDatabaseUseCase.invoke(it)
                    LceState.Success(it)
                }, onFailure = { LceState.Fail(it) }
            )
        }
    }.onStart {
        val storedList = getAllMoviesFromDatabaseUseCase.invoke()
        val state = if (storedList.isNotEmpty()) {
            LceState.Success(storedList)
        } else {
            LceState.Loading
        }
        emit(Result.success(state))
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    fun onRefreshed() {
        refreshFlow.tryEmit(Unit)
    }

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    init {
        onRefreshed()
    }

    fun onSwipeMovie(favorite: Favorite) = viewModelScope.launch {
        insertFavoriteUseCase.invoke(favorite)
    }
}