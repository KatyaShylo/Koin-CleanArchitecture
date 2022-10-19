package com.example.mykinopoisk.data.di

import com.example.mykinopoisk.domain.usecase.favorite.*
import com.example.mykinopoisk.domain.usecase.movie.*
import com.example.mykinopoisk.domain.usecase.note.DeleteNoteUseCase
import com.example.mykinopoisk.domain.usecase.note.GetAllNoteUseCase
import com.example.mykinopoisk.domain.usecase.note.InsertNoteUseCase
import com.example.mykinopoisk.domain.usecase.note.SubscribeUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetAllMoviesUseCase)
    singleOf(::GetMovieDetailUseCase)
    singleOf(::GetAllMoviesFromDatabaseUseCase)
    singleOf(::InsertMoviesInDatabaseUseCase)
    singleOf(::DeleteMovieFromDatabaseUseCase)

    singleOf(::GetAllNoteUseCase)
    singleOf(::InsertNoteUseCase)
    singleOf(::SubscribeUseCase)
    singleOf(::DeleteNoteUseCase)

    singleOf(::SubscribeToFavoriteUseCase)
    singleOf(::DeleteFavoriteUseCase)
    singleOf(::InsertFavoriteUseCase)
    singleOf(::InsertAllFavoriteUseCase)
    singleOf(::GetAllFavoritesUseCase)

}