package com.example.mykinopoisk.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favouriteLocalRepository: FavoriteLocalRepository
) : ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<Flow<List<Favorite>>>>(LceState.Loading)
    val lce: Flow<LceState<Flow<List<Favorite>>>> = _lceFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val state = favouriteLocalRepository.subscribeToFavorite()
                .fold(
                    onSuccess = { LceState.Success(it) },
                    onFailure = { LceState.Fail(it) }
                )
            _lceFlow.tryEmit(state)
        }
    }

    fun onFavoriteSwipe(favorite: Favorite) = viewModelScope.launch {
        favouriteLocalRepository.deleteFavorite(favorite)

    }
}