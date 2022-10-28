package com.example.mykinopoisk.presentation.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mykinopoisk.databinding.ItemFavoriteBinding
import com.example.mykinopoisk.domain.model.favorite.Favorite

class FavoriteAdapter(
    context: Context
) : ListAdapter<Favorite, FavoriteViewHolder>(
    DIFF_UTIL
) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            binding = ItemFavoriteBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return (oldItem.rating == newItem.rating &&
                        oldItem.name == newItem.name &&
                        oldItem.poster == newItem.poster)
            }
        }
    }
}