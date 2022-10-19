package com.example.mykinopoisk.presentation.ui.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentAddNoteBinding
import com.example.mykinopoisk.domain.model.note.Note
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MakeNoteFragment : DialogFragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<MakeNoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddNoteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        with(binding) {

            setNullError()

            getError(editTextMovieName, containerMovieName)
            getError(editTextMovieComment, containerMovieComment)

            buttonOk.setOnClickListener {

                val noteNameMovie = editTextMovieName.text.toString()
                val noteCommentMovie = editTextMovieComment.text.toString()

                if (containerMovieName.error != null || containerMovieComment.error != null) {
                    AlertDialog.Builder(requireContext())
                        .setTitle(context?.resources?.getString(R.string.title_dialog_incorrect_entry))
                        .setMessage(context?.resources?.getString(R.string.message_dialog_incorrect_entry))
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .show()
                }

                if (containerMovieName.error == null && containerMovieComment.error == null
                    && noteNameMovie.isNotEmpty() && noteCommentMovie.isNotEmpty()
                ) {

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.onButtonAddNote(
                            Note(
                                nameMovie = noteNameMovie, comment = noteCommentMovie
                            )
                        )
                    }

                    AlertDialog.Builder(requireContext())
                        .setTitle(context?.resources?.getString(R.string.title_dialog_successful))
                        .setMessage(
                            editTextMovieComment.text.toString()
                        )
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .show()
                    findNavController().navigate(R.id.to_note_movie_fragment)

                    setNullText()
                    setNullError()
                }
            }
            setNullError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNullError() {
        binding.containerMovieName.error = null
        binding.containerMovieComment.error = null
    }

    private fun setNullText() {
        binding.editTextMovieName.setText("")
        binding.editTextMovieComment.setText("")
    }

    private fun getError(
        editText: TextView,
        textInputLayoutContainer: TextInputLayout
    ) {
        editText.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (!text.matches("[a-zA-Zа-яА-Я0-9\\s\\p{P}\\p{S}]+".toRegex())) {
                    textInputLayoutContainer.error = context?.resources?.getString(R.string.validator_error_not_null)
                } else {
                    textInputLayoutContainer.error = null
                }
            } else {
                textInputLayoutContainer.error = context?.resources?.getString(R.string.validator_error_null)
            }
        }
    }
}

