package com.example.mykinopoisk.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.model.movie.Movie
import com.example.mykinopoisk.domain.usecase.favorite.DeleteFavoriteUseCase
import com.example.mykinopoisk.domain.usecase.favorite.GetAllFavoritesUseCase
import com.example.mykinopoisk.domain.usecase.favorite.InsertAllFavoriteUseCase
import com.example.mykinopoisk.domain.usecase.favorite.SubscribeToFavoriteUseCase
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val subscribeToFavoriteUseCase: SubscribeToFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {


    private val _lceFlow = MutableStateFlow<LceState<Flow<List<Favorite>>>>(LceState.Loading)
    val lce: Flow<LceState<Flow<List<Favorite>>>> = _lceFlow.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeToFavoriteUseCase.invoke().fold(
                onSuccess = { _lceFlow.tryEmit(LceState.Success(it)) },
                onFailure = { emptyList<Movie>() }
            )
        }
    }

    fun onFavoriteSwipe(favorite: Favorite) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(favorite)
    }

}