package com.example.mykinopoisk.data.database

import androidx.room.*
import com.example.mykinopoisk.data.model.favourite.FavoriteEntity
import com.example.mykinopoisk.data.model.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteDao {

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favoriteentity")
    fun subscribeToFavorite(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: List<FavoriteEntity>)

    @Query("SELECT * FROM favoriteentity")
    suspend fun getAllFavorites(): List<FavoriteEntity>



}