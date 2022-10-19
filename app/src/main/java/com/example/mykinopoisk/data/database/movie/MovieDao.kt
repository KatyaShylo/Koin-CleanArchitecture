package com.example.mykinopoisk.data.database.movie

import androidx.room.*
import com.example.mykinopoisk.data.model.movie.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentity")
    suspend fun getAllMovie(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

}