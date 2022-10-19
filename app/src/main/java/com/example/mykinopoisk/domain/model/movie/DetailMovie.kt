package com.example.mykinopoisk.domain.model.movie

data class DetailMovie(
    val poster: Poster? = null,
    val rating: Rating,
    val id: Long,
    val type: String,
    val name: String,
    val description: String? = null,
    val year: Long,
    val alternativeName: String? = null
)