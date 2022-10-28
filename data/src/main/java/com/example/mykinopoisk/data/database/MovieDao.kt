package com.example.mykinopoisk.data.database

import androidx.room.*
import com.example.mykinopoisk.data.model.movie.MovieEntity

@Dao
internal interface MovieDao {

    @Query("SELECT * FROM movieentity")
    suspend fun getAllMovie(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

}