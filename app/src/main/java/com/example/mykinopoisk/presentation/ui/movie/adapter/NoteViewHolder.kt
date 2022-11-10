package com.example.mykinopoisk.presentation.ui.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.databinding.ItemNoteBinding
import com.example.mykinopoisk.domain.model.note.Note

class NoteViewHolder(
    private val binding: ItemNoteBinding,
    private val onLongItemClicked: (Note) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Note) {
        with(binding) {
            noteNameMovie.text = item.nameMovie
            noteCommentMovie.text = item.comment

            root.setOnLongClickListener {
                onLongItemClicked(item)
                true
            }
        }
    }
}