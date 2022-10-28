package com.example.mykinopoisk.data.model.movie

import com.google.gson.annotations.SerializedName

internal data class RatingDTO(
    @SerializedName("kp")
    val rating: Double
)