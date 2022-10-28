package com.example.mykinopoisk.presentation.ui.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mykinopoisk.databinding.ItemFavoriteBinding
import com.example.mykinopoisk.domain.model.favorite.Favorite

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Favorite) {
        with(binding) {
            posterMovie.load(item.poster)
            ratingMovie.text = item.rating.rating.toString()
            titleMovieFirst.text = item.name

        }
    }
}