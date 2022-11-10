package com.example.mykinopoisk.presentation.ui.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentAddNoteBinding
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.presentation.ui.extension.createWindowInsetsForToolbar
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
            toolbarFragmentMakeNote.createWindowInsetsForToolbar()

            toolbarFragmentMakeNote.setNavigationOnClickListener {
                findNavController().navigate(R.id.to_note_movie_fragment)
            }

            setNullError()

            getError(editTextMovieName, containerMovieName)
            getError(editTextMovieComment, containerMovieComment)


            buttonOk.setOnClickListener {

                val noteName = editTextMovieName.text.toString()
                val noteComment = editTextMovieComment.text.toString()

                if (containerMovieName.error != null || containerMovieComment.error != null ||
                    noteComment.isEmpty() || noteName.isEmpty()
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.title_dialog_incorrect_entry)
                        .setMessage(R.string.message_dialog_incorrect_entry)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            getError(editTextMovieName, containerMovieName)
                            getError(editTextMovieComment, containerMovieComment)
                        }
                        .show()
                }

                if (containerMovieName.error == null && containerMovieComment.error == null
                    && noteName.isNotEmpty() && noteComment.isNotEmpty()
                ) {

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.onButtonAddNote(
                            Note(nameMovie = noteName, comment = noteComment)
                        )
                    }

                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.title_dialog_successful)
                        .setMessage(
                            editTextMovieName.text.toString()
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
        if (editText.text.isEmpty() || textInputLayoutContainer.isEmpty()) {
            textInputLayoutContainer.error =
                requireContext().resources.getString(R.string.validator_error_null)
        }
        editText.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                textInputLayoutContainer.error = null
            }
        }
    }
}

