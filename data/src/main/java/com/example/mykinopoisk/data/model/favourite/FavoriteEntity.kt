package com.example.mykinopoisk.data.model.favourite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val poster: String? = null,
    val rating: Double,
    val name: String,
    val description: String? = null,
    val year: Long,
    @ColumnInfo(name = "alternative_name")
    val alternativeName: String? = null
)
