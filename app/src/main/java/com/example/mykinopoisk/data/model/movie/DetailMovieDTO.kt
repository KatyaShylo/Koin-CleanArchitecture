package com.example.mykinopoisk.data.model.movie

data class DetailMovieDTO(
    val poster: PosterDTO? = null,
    val rating: RatingDTO,
    val id: Long,
    val type: String,
    val name: String,
    val description: String? = null,
    val year: Long,
    val alternativeName: String? = null
)
