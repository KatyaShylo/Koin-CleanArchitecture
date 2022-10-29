package com.example.mykinopoisk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mykinopoisk.data.model.favourite.FavoriteEntity
import com.example.mykinopoisk.data.model.movie.MovieEntity
import com.example.mykinopoisk.data.model.note.NoteEntity

@Database(entities = [MovieEntity::class, FavoriteEntity::class, NoteEntity::class], version = 1)
internal abstract class MyKinopoiskDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun favoriteDao(): FavoriteDao

    abstract fun noteDao(): NoteDao
}