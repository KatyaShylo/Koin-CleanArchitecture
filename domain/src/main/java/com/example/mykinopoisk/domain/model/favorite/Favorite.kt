package com.example.mykinopoisk.domain.model.favorite

import com.example.mykinopoisk.domain.model.movie.Rating

data class Favorite(
    val _id: Int = 0,
    val poster: String? = null,
    val rating: Rating,
    val id: Long,
    val name: String,
    val description: String? = null,
    val year: Long,
    val alternativeName: String? = null
)
