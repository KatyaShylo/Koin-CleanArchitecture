package com.example.mykinopoisk.data.model.movie

import com.google.gson.annotations.SerializedName

data class RatingDTO(
    @SerializedName("kp")
    val rating: Double
)