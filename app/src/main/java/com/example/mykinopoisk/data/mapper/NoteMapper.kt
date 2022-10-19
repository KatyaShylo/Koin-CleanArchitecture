package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.note.NoteEntity
import com.example.mykinopoisk.domain.model.note.Note

fun NoteEntity.toDomainModel(): Note {
    return Note(
        id, nameMovie, comment
    )
}

fun Note.toEntityModel(): NoteEntity {
    return NoteEntity(
        id, nameMovie, comment
    )
}