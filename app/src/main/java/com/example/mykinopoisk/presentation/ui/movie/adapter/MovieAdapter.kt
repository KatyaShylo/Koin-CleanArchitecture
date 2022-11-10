package com.example.mykinopoisk.presentation.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mykinopoisk.databinding.ItemMovieBinding
import com.example.mykinopoisk.domain.model.movie.Movie

class MovieAdapter(
    context: Context,
    private val onMovieClicked: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            binding = ItemMovieBinding.inflate(layoutInflater, parent, false),
            onMovieClicked = onMovieClicked
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val position = getItem(position)
        holder.bind(position)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem:Movie, newItem: Movie): Boolean {
                return (oldItem.rating == newItem.rating &&
                        oldItem.name == newItem.name &&
                        oldItem.poster == newItem.poster)
            }
        }
    }
}