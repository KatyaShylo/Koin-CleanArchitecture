package com.example.mykinopoisk.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mykinopoisk.databinding.ItemMovieBinding
import com.example.mykinopoisk.domain.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onMovieClicked: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie) {
        with(binding) {
            posterMovie.load(item.poster?.posterUrl)
            ratingMovie.text = item.rating.rating.toString()
            titleMovieFirst.text = item.name

            root.setOnClickListener {
                onMovieClicked(item)
            }
        }
    }
}