package com.example.mykinopoisk.data.repository.favorite

import com.example.mykinopoisk.data.database.FavoriteDao
import com.example.mykinopoisk.data.mapper.toDomainModel
import com.example.mykinopoisk.data.mapper.toFavoriteEntityModel
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FavoriteLocalRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteLocalRepository {

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite = favorite.toFavoriteEntityModel())
    }

    override suspend fun subscribeToFavorite(): Result<Flow<List<Favorite>>> = runCatching {
        favoriteDao.subscribeToFavorite().map {
            it.map { favoriteEntity ->
                favoriteEntity.toDomainModel()
            }
        }
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite = favorite.toFavoriteEntityModel())
    }

    override suspend fun insertFavorite(favorite: List<Favorite>) {
        return favoriteDao.insertFavorite(favorite.map { it.toFavoriteEntityModel() })
    }

    override suspend fun getAllFavorites(): List<Favorite> {
        return favoriteDao.getAllFavorites().map { favoriteEntity ->
            favoriteEntity.toDomainModel()
        }
    }
}
