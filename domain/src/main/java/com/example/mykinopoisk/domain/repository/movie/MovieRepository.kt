package com.example.mykinopoisk.domain.repository.movie

import com.example.mykinopoisk.domain.model.movie.DetailMovie
import com.example.mykinopoisk.domain.model.movie.ListMovie

interface MovieRepository {

    suspend fun getMovies(
        token: String,
        limit: String,
        fieldYear: String,
        searchYear: String,
        fieldRating: String,
        searchRating: String
    ): Result<ListMovie>

    suspend fun getMovieDetail(
        token: String,
        fieldId: String,
        searchId: String
    ): Result<DetailMovie>
}