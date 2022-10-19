package com.example.mykinopoisk.data.database.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mykinopoisk.data.model.favourite.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

