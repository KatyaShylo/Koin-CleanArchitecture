package com.example.mykinopoisk.data.di

import androidx.room.Room
import com.example.mykinopoisk.data.database.MyKinopoiskDatabase

import org.koin.dsl.module

internal val databaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            MyKinopoiskDatabase::class.java,
            "database"
        ).build()
    }

    single { get<MyKinopoiskDatabase>().noteDao() }
    single { get<MyKinopoiskDatabase>().favoriteDao() }
    single { get<MyKinopoiskDatabase>().movieDao() }

}