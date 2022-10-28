package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.movie.*
import com.example.mykinopoisk.domain.model.movie.*


internal fun ListMovieDTO.toDomainModel(): ListMovie {
    return ListMovie(listMovie.toDomainModel())
}

internal fun List<MovieDTO>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

internal fun MovieDTO.toDomainModel(): Movie {
    return Movie(
        poster = poster?.toDomainModel(),
        rating = rating.toDomainModel(),
        id = id,
        name = name,
        description = description,
        year = year,
        alternativeName = alternativeName
    )
}

internal fun DetailMovieDTO.toDomainModel(): DetailMovie {
    return DetailMovie(
        poster = poster?.toDomainModel(),
        rating = rating.toDomainModel(),
        id = id,
        type = type,
        name = name,
        description = description,
        year = year,
        alternativeName = alternativeName
    )
}

internal fun PosterDTO.toDomainModel(): Poster {
    return Poster(
        posterUrl = posterUrl
    )
}

internal fun RatingDTO.toDomainModel(): Rating {
    return Rating(
        rating = rating
    )
}

internal fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        poster = Poster(posterUrl = ""),
        rating = Rating(rating),
        id = id,
        name = name,
        description = description,
        year = year,
        alternativeName = alternativeName
    )
}

internal fun Movie.toEntityModel(): MovieEntity {
    return MovieEntity(
        id = id,
        poster = poster?.posterUrl,
        rating = rating.rating,
        name = name,
        alternativeName = alternativeName,
        year = year,
        description = description
    )
}