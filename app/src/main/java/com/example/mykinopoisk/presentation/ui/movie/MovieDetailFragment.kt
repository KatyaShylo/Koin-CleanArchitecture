package com.example.mykinopoisk.presentation.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentDetailMovieBinding
import com.example.mykinopoisk.presentation.model.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val viewModel by viewModel<MovieDetailViewModel> {
        parametersOf(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailMovieBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbarFragmentDetailMovie.setNavigationOnClickListener {
                findNavController().navigate(R.id.fragment_movies)
            }

            viewModel.movieDetailFlow
                .onEach { lceState ->
                    when (lceState) {
                        LceState.Loading -> {
                            loading.isVisible = true
                        }
                        is LceState.Success -> {
                            loading.isVisible = false
                            cardDetailMovie.isVisible = true
                            description.isVisible = true
                            imagePosterMovie.load(lceState.data.poster?.posterUrl)
                            textNameMovieFirst.text = lceState.data.name
                            textNameMovieSecond.text = lceState.data.alternativeName
                            ratingMovie.text =
                                context?.resources?.getString(R.string.kp) + " ${lceState.data.rating.rating}"
                            yearMovie.text = lceState.data.year.toString()
                            description.text = lceState.data.description
                        }
                        is LceState.Fail -> {
                            Toast.makeText(
                                requireContext(),
                                lceState.error.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
        createWindowInsets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarFragmentDetailMovie) { _, insets ->
            val systemBarInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbarFragmentDetailMovie.updatePadding(
                top = systemBarInset.top,
                left = systemBarInset.left,
                right = systemBarInset.right
            )
            insets
        }
    }
}