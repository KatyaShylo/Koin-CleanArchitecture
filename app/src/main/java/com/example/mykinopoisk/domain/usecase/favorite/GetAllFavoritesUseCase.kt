package com.example.mykinopoisk.domain.usecase.favorite

import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository

class GetAllFavoritesUseCase(
    private val repository: FavoriteLocalRepository
) {

    suspend operator fun invoke(): List<Favorite> {
        return repository.getAllFavorites()
    }
}