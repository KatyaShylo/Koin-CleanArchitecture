package com.example.mykinopoisk.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val poster: String? = null,
    val rating: Double,
    val name: String,
    val description: String? = null,
    val year: Long,
    @ColumnInfo(name = "alternative_name")
    val alternativeName: String? = null
)
