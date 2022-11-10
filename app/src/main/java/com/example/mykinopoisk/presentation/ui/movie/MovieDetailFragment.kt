package com.example.mykinopoisk.presentation.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentDetailMovieBinding
import com.example.mykinopoisk.domain.model.movie.Movie
import com.example.mykinopoisk.presentation.model.LceState
import com.example.mykinopoisk.presentation.ui.extension.createWindowInsetsForToolbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File

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

            toolbarFragmentDetailMovie.createWindowInsetsForToolbar()

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

            iconShareTitle.setOnClickListener {
                saveMovieToSend(textNameMovieFirst.text.toString() ?: "")

                val sendMove = getMovieToSend() ?: return@setOnClickListener

                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.mykinopoisk.provider",
                    sendMove
                )

                val intent = Intent(Intent.ACTION_SEND)
                    .setType("*/*")
                    .putExtra(Intent.EXTRA_STREAM, uri)
                requireContext().startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveMovieToSend(text: String) {
        requireContext()
            .openFileOutput("text.txt", Context.MODE_PRIVATE)
            .use { stream ->
                stream.write(text.toByteArray())
            }
    }

    private fun getMovieToSend(): File? {
        return requireContext()
            .filesDir
            .listFiles()
            ?.find { it.name == "text.txt" }
    }
}