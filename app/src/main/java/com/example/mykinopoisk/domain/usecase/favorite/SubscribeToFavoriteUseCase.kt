package com.example.mykinopoisk.domain.usecase.favorite

import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository
import kotlinx.coroutines.flow.Flow

class SubscribeToFavoriteUseCase(
    private val repository: FavoriteLocalRepository
) {
    suspend operator fun invoke(): Result<Flow<List<Favorite>>> {
        return repository.subscribeToFavorite()
    }
}