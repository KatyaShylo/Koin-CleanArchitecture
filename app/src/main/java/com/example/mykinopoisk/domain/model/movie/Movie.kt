package com.example.mykinopoisk.domain.model.movie

data class Movie(
    val poster: Poster? = null,
    val rating: Rating,
    val id: Long,
    val name: String,
    val description: String? = null,
    val year: Long,
    val alternativeName: String? = null
)
