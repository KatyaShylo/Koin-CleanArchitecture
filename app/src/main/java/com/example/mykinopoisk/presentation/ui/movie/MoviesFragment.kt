package com.example.mykinopoisk.presentation.ui.movie

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentMoviesBinding
import com.example.mykinopoisk.domain.model.favorite.Favorite
import com.example.mykinopoisk.presentation.model.LceState
import com.example.mykinopoisk.presentation.ui.movie.adapter.MovieAdapter
import com.example.mykinopoisk.presentation.ui.extension.SwipeElement
import com.example.mykinopoisk.presentation.ui.extension.addHorizontalSpaceDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<MoviesViewModel>()

    private val adapter by lazy {
        MovieAdapter(requireContext())
        { movie ->
            findNavController().navigate(
                MoviesFragmentDirections.toDetailMovieFragment(
                    movie.id
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoviesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerViewMovies.adapter = adapter
            recyclerViewMovies.addHorizontalSpaceDecoration(R.dimen.space_horizontal_decorator)

            with(toolbarFragmentMovies) {
                menu.findItem(R.id.search)
                    .actionView
                    .let { it as SearchView }
                    .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean = false

                        override fun onQueryTextChange(query: String): Boolean {
                            viewModel.onQueryChanged(query)
                            return true
                        }
                    })
            }

            swipeRecyclerView.setOnRefreshListener {
                adapter.submitList(emptyList())
                viewModel.onRefreshed()
            }

            viewModel.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { swipeRecyclerView.isRefreshing }
                .onEach { lceState ->
                    lceState.map { lce ->
                        when (lce) {
                            LceState.Loading -> {
                                loading.isVisible = true
                            }
                            is LceState.Success -> {
                                loading.isVisible = false
                                swipeRecyclerView.isVisible = true
                                adapter.submitList(lce.data)
                            }
                            is LceState.Fail -> {
                                Toast.makeText(
                                    requireContext(),
                                    lce.error.message ?: "State error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            val swipeToFavorite = object : SwipeElement() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val movieToFavorite = adapter.currentList[position]
                    viewLifecycleOwner.lifecycleScope.launch {
                        AlertDialog.Builder(requireContext())
                            .setTitle(context?.resources?.getString(R.string.add_favorite))
                            .setMessage(context?.resources?.getString(R.string.add_message_favorite))
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                viewModel.onSwipeMovie(
                                    Favorite(
                                        poster = movieToFavorite?.poster?.posterUrl,
                                        rating = movieToFavorite.rating,
                                        id = movieToFavorite.id,
                                        name = movieToFavorite.name,
                                        description = movieToFavorite.description,
                                        year = movieToFavorite.year,
                                        alternativeName = movieToFavorite.alternativeName
                                    )
                                )
                                adapter.notifyDataSetChanged()
                            }
                            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                                adapter.notifyDataSetChanged()
                                dialog.cancel()
                            }
                            .show()
                    }
                }
            }
            ItemTouchHelper(swipeToFavorite).attachToRecyclerView(recyclerViewMovies)
        }
        createWindowInsets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarFragmentMovies) { _, insets ->
            val systemBarInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbarFragmentMovies.updatePadding(
                top = systemBarInset.top,
                left = systemBarInset.left,
                right = systemBarInset.right
            )
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerViewMovies) { _, insets ->
            val systemBarInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.recyclerViewMovies.updatePadding(
                bottom = systemBarInset.bottom,
                right = systemBarInset.right,
                left = systemBarInset.left
            )
            insets
        }
    }
}