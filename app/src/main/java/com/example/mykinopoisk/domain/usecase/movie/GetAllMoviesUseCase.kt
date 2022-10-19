package com.example.mykinopoisk.domain.usecase.movie

import com.example.mykinopoisk.domain.model.movie.ListMovie
import com.example.mykinopoisk.domain.repository.movie.MovieRepository

class GetAllMoviesUseCase(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(
        token: String,
        limit: String,
        fieldYear: String,
        searchYear: String,
        fieldRating: String,
        searchRating: String
    ): Result<ListMovie> {
        return repository.getMovies(token, limit, fieldYear, searchYear, fieldRating, searchRating)
    }
}