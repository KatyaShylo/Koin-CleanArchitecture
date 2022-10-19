package com.example.mykinopoisk.data.model.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameMovie: String,
    val comment: String
)
