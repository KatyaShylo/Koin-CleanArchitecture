package com.example.mykinopoisk.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.databinding.ItemNoteBinding
import com.example.mykinopoisk.domain.model.note.Note

class NoteViewHolder(
    private val binding: ItemNoteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Note) {
        with(binding) {
            noteNameMovie.text = item.nameMovie
            noteCommentMovie.text = item.comment
        }
    }
}