package com.example.mykinopoisk.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository
import com.example.mykinopoisk.domain.repository.movie.MovieLocalRepository
import com.example.mykinopoisk.domain.repository.movie.MovieRepository
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val movieLocalRepository: MovieLocalRepository,
    private val favoriteLocalRepository: FavoriteLocalRepository
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val dataFlow = queryFlow.combine(
        refreshFlow.map {
            runCatching {
                movieRepository.getMovies(
                    TOKEN, LIMIT, FIELD_YEAR, SEARCH_YEAR, FIELD_RATING, SEARCH_RATING
                )
            }.onSuccess { LceState.Success(it) }
                .onFailure { LceState.Fail(it) }
        }
    ) { query, result ->
        result.map {
            it.map { it.listMovie.filter { it.name.contains(query, ignoreCase = true) } }.fold(
                onSuccess = {
                    movieLocalRepository.insertMoviesInDatabase(it)
                    LceState.Success(it)
                }, onFailure = { LceState.Fail(it) }
            )
        }
    }.onStart {
        val storedList = movieLocalRepository.getAllMoviesFromDatabase()
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

    fun onSwipeMovie(favorite: Favorite) =
        viewModelScope.launch {

            favoriteLocalRepository.insertFavorite(favorite)
        }

    companion object {
        private const val TOKEN = "PR0J8XR-XTCM5Y8-P3RHWPK-WYEKR6W"
        private const val LIMIT = "50"
        private const val FIELD_YEAR = "year"
        private const val SEARCH_YEAR = "2019-2022"
        private const val FIELD_RATING = "rating.kp"
        private const val SEARCH_RATING = "8-10"
    }
}