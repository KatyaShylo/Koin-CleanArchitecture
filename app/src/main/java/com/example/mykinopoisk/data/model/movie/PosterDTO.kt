package com.example.mykinopoisk.data.model.movie

import com.google.gson.annotations.SerializedName

data class PosterDTO(
    @SerializedName("url")
    val posterUrl: String
)
