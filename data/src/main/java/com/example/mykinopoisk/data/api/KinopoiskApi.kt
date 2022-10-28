package com.example.mykinopoisk.data.api

import com.example.mykinopoisk.data.model.movie.DetailMovieDTO
import com.example.mykinopoisk.data.model.movie.ListMovieDTO
import retrofit2.http.GET
import retrofit2.http.Query

internal interface KinopoiskApi {

    @GET("movie")
    suspend fun getMovies(
        @Query("token") token: String,
        @Query("limit") limit: String,
        @Query("field") fieldYear: String,
        @Query("search") searchYear: String,
        @Query("field") fieldRating: String,
        @Query("search") searchRating: String
    ): ListMovieDTO

    @GET("movie")
    suspend fun getMovieDetail(
        @Query("token") token: String,
        @Query("field") fieldId: String,
        @Query("search") searchId: String
    ): DetailMovieDTO

}