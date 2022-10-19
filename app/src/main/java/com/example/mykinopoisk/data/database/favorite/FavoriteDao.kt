package com.example.mykinopoisk.data.database.favorite

import androidx.room.*
import com.example.mykinopoisk.data.model.favourite.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favoriteentity")
    fun subscribeToFavorite(): Flow<List<FavoriteEntity>>

    @Insert
    suspend fun insertFavorite(favorite: FavoriteEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: List<FavoriteEntity>)

    @Query("SELECT * FROM favoriteentity")
    suspend fun getAllFavorites(): List<FavoriteEntity>

}