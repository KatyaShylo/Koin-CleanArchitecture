package com.example.mykinopoisk.data.repository.movie

import com.example.mykinopoisk.data.api.KinopoiskApi
import com.example.mykinopoisk.data.mapper.toDomainModel
import com.example.mykinopoisk.domain.model.movie.DetailMovie
import com.example.mykinopoisk.domain.model.movie.ListMovie
import com.example.mykinopoisk.domain.repository.movie.MovieRepository

internal class MovieRepositoryImpl(
    private val kinopoiskApi: KinopoiskApi
) : MovieRepository {

    override suspend fun getMovies(
        token: String,
        limit: String,
        fieldYear: String,
        searchYear: String,
        fieldRating: String,
        searchRating: String
    ): Result<ListMovie> = runCatching {
        kinopoiskApi.getMovies(token, limit, fieldYear, searchYear, fieldRating, searchRating)
    }.map { listMovieDTO ->
        listMovieDTO.toDomainModel()
    }

    override suspend fun getMovieDetail(
        token: String,
        fieldId: String,
        searchId: String
    ): Result<DetailMovie> = runCatching {
        kinopoiskApi.getMovieDetail(token, fieldId, searchId)
    }.map { detailMovieDTO ->
        detailMovieDTO.toDomainModel()
    }
}