package com.example.mykinopoisk.domain.usecase.movie

import com.example.mykinopoisk.domain.model.movie.Movie
import com.example.mykinopoisk.domain.repository.movie.MovieLocalRepository

class GetAllMoviesFromDatabaseUseCase(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getAllMoviesFromDatabase()
    }
}