package com.example.mykinopoisk.data.database.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mykinopoisk.data.model.movie.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}