package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.favourite.FavoriteEntity
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.model.movie.Rating

internal fun FavoriteEntity.toDomainModel(): Favorite {
    return Favorite(
        poster = poster,
        rating = Rating(rating),
        id = id.toLong(),
        name = name,
        description = description,
        year = year,
        alternativeName = alternativeName
    )
}

internal fun Favorite.toFavoriteEntityModel(): FavoriteEntity {
    return FavoriteEntity(
        id = id.toInt(),
        poster = poster,
        rating = rating.rating,
        name = name,
        alternativeName = alternativeName,
        year = year,
        description = description
    )
}