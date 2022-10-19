package com.example.mykinopoisk.presentation.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentFavoritesBinding
import com.example.mykinopoisk.presentation.model.LceState
import com.example.mykinopoisk.presentation.ui.adapter.FavoriteAdapter
import com.example.mykinopoisk.presentation.ui.extension.SwipeElement
import com.example.mykinopoisk.presentation.ui.extension.addHorizontalSpaceDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<FavoritesViewModel>()

    private val adapter by lazy {
        FavoriteAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            recyclerViewMovies.adapter = adapter
            recyclerViewMovies.addHorizontalSpaceDecoration(R.dimen.space_horizontal_decorator)

            viewModel
                .lce
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { lce ->
                    when (lce) {
                        is LceState.Fail -> {
                            Toast.makeText(requireContext(), lce.error.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is LceState.Success -> {
                            loading.isVisible = false
                            swipeRecyclerViewFavorite.isVisible = true
                            lce.data.collect {
                                adapter.submitList(it)
                            }
                        }
                        LceState.Loading -> {
                            loading.isVisible = true
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            val swipeToDeleteCallback = object : SwipeElement() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val favoriteToDel = adapter.currentList[viewHolder.adapterPosition]
                    viewLifecycleOwner.lifecycleScope.launch {

                        AlertDialog.Builder(requireContext())
                            .setTitle(context?.resources?.getString(R.string.title_dialog_delete_favorite))
                            .setMessage(context?.resources?.getString(R.string.message_dialog_delete))
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                viewModel.onFavoriteSwipe(favoriteToDel)
                                Toast.makeText(
                                    requireContext(),
                                    context?.resources?.getString(R.string.toast_text_delete_favorite),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                                adapter.notifyDataSetChanged()
                                Toast.makeText(
                                    requireContext(),
                                    context?.resources?.getString(R.string.toast_text_not_delete_favorite),
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.cancel()
                            }
                            .show()
                    }
                }
            }
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.recyclerViewMovies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}