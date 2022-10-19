package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.favourite.FavoriteEntity
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.domain.model.movie.Rating

fun FavoriteEntity.toDomainModel(): Favorite {
    return Favorite(
        _id = _id,
        poster = poster,
        rating = Rating(rating),
        id = id,
        name = name,
        description = description,
        year = year,
        alternativeName = alternativeName
    )
}

fun Favorite.toFavoriteEntityModel(): FavoriteEntity {
    return FavoriteEntity(
        _id = _id,
        id = id,
        poster = poster,
        rating = rating.rating,
        name = name,
        alternativeName = alternativeName,
        year = year,
        description = description
    )
}