package com.example.mykinopoisk.data.model.movie

internal data class MovieDTO(
    val poster: PosterDTO? = null,
    val rating: RatingDTO,
    val id: Long,
    val name: String,
    val description: String? = null,
    val year: Long,
    val alternativeName: String? = null
)