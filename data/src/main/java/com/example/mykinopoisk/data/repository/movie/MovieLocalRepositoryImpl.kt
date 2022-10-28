package com.example.mykinopoisk.data.repository.movie

import com.example.mykinopoisk.data.database.MovieDao
import com.example.mykinopoisk.data.mapper.toDomainModel
import com.example.mykinopoisk.data.mapper.toEntityModel
import com.example.mykinopoisk.domain.model.movie.Movie
import com.example.mykinopoisk.domain.repository.movie.MovieLocalRepository

internal class MovieLocalRepositoryImpl(
    private val movieDao: MovieDao
) : MovieLocalRepository {

    override suspend fun getAllMoviesFromDatabase(): List<Movie> {
        return movieDao.getAllMovie().map { it.toDomainModel() }
    }

    override suspend fun insertMoviesInDatabase(movies: List<Movie>) {
        return movieDao.insertMovies(movies.map { it.toEntityModel() })
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie.toEntityModel())
    }
}