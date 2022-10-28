package com.example.mykinopoisk.data.model.movie

import com.google.gson.annotations.SerializedName

internal data class ListMovieDTO(
    @SerializedName("docs")
    val listMovie: List<MovieDTO>
)