package com.example.mykinopoisk.domain.usecase.movie

import com.example.mykinopoisk.domain.model.movie.DetailMovie
import com.example.mykinopoisk.domain.repository.movie.MovieRepository

class GetMovieDetailUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        token: String,
        fieldId: String,
        searchId: String
    ): Result<DetailMovie> {
        return repository.getMovieDetail(token, fieldId, searchId)
    }
}