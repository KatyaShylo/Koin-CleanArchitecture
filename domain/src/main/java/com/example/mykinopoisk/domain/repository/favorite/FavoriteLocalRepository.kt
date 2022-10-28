package com.example.mykinopoisk.domain.repository.favorite

import com.example.mykinopoisk.domain.model.favorite.Favorite
import kotlinx.coroutines.flow.Flow


interface FavoriteLocalRepository {

    suspend fun deleteFavorite(favorite: Favorite)

    suspend fun subscribeToFavorite(): Result<Flow<List<Favorite>>>

    suspend fun insertFavorite(favorite: Favorite)

    suspend fun insertFavorite(favorite: List<Favorite>)

    suspend fun getAllFavorites(): List<Favorite>

}