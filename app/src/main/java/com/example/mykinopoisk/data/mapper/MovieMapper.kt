package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.movie.*
import com.example.mykinopoisk.domain.model.movie.*


fun ListMovieDTO.toDomainModel(): ListMovie {
    return ListMovie(listMovie.toDomainModel())
}

fun List<MovieDTO>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

fun MovieDTO.toDomainModel(): Movie {
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

fun DetailMovieDTO.toDomainModel(): DetailMovie {
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

fun PosterDTO.toDomainModel(): Poster {
    return Poster(
        posterUrl = posterUrl
    )
}

fun RatingDTO.toDomainModel(): Rating {
    return Rating(
        rating = rating
    )
}

fun MovieEntity.toDomainModel(): Movie {
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

fun Movie.toEntityModel(): MovieEntity {
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