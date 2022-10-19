package com.example.mykinopoisk.domain.repository.movie

import com.example.mykinopoisk.domain.model.movie.Movie

interface MovieLocalRepository {

    suspend fun getAllMoviesFromDatabase(): List<Movie>

    suspend fun insertMoviesInDatabase(movies: List<Movie>)

    suspend fun deleteMovie(movie: Movie)

}