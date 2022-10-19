package com.example.mykinopoisk.data.di

import androidx.room.Room
import com.example.mykinopoisk.data.database.favorite.FavoriteDatabase
import com.example.mykinopoisk.data.database.movie.MovieDatabase
import com.example.mykinopoisk.data.database.note.NoteDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            "database"
        ).build()
    }

    single { get<MovieDatabase>().movieDao() }

    // DB - note
    single {
        Room.databaseBuilder(
            get(),
            NoteDatabase::class.java,
            "database_note"
        ).build()
    }

    single { get<NoteDatabase>().noteDao() }

    //DB - favorite
    single {
        Room.databaseBuilder(
            get(),
            FavoriteDatabase::class.java,
            "database_fa"
        ).build()
    }

    single { get<FavoriteDatabase>().favoriteDao() }
}