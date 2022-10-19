package com.example.mykinopoisk.presentation.ui.di

import com.example.mykinopoisk.presentation.ui.favorite.FavoritesViewModel
import com.example.mykinopoisk.presentation.ui.movie.MovieDetailViewModel
import com.example.mykinopoisk.presentation.ui.movie.MoviesViewModel
import com.example.mykinopoisk.presentation.ui.note.MakeNoteViewModel
import com.example.mykinopoisk.presentation.ui.note.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::MakeNoteViewModel)
    viewModelOf(::NoteViewModel)
}