package com.example.mykinopoisk.data.di

import com.example.mykinopoisk.data.repository.favorite.FavoriteLocalRepositoryImpl
import com.example.mykinopoisk.data.repository.movie.MovieLocalRepositoryImpl
import com.example.mykinopoisk.data.repository.movie.MovieRepositoryImpl
import com.example.mykinopoisk.data.repository.note.NoteLocalRepositoryImpl
import com.example.mykinopoisk.domain.repository.favorite.FavoriteLocalRepository
import com.example.mykinopoisk.domain.repository.movie.MovieLocalRepository
import com.example.mykinopoisk.domain.repository.movie.MovieRepository
import com.example.mykinopoisk.domain.repository.note.NoteLocalRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::MovieRepositoryImpl) {
        bind<MovieRepository>()
    }

    singleOf(::MovieLocalRepositoryImpl) {
        bind<MovieLocalRepository>()
    }

    // repository - Note
    singleOf(::NoteLocalRepositoryImpl) {
        bind<NoteLocalRepository>()
    }

    // repository - Favorite
    singleOf(::FavoriteLocalRepositoryImpl) {
        bind<FavoriteLocalRepository>()
    }
}