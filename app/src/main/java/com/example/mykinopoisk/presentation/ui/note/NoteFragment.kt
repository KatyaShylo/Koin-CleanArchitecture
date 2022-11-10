package com.example.mykinopoisk.presentation.ui.note

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mykinopoisk.R

import com.example.mykinopoisk.databinding.FragmentNoteBinding
import com.example.mykinopoisk.domain.model.note.Note
import com.example.mykinopoisk.presentation.model.LceState
import com.example.mykinopoisk.presentation.ui.extension.SwipeElement
import com.example.mykinopoisk.presentation.ui.extension.addHorizontalSpaceDecoration
import com.example.mykinopoisk.presentation.ui.extension.createWindowInsetsForRecycleView
import com.example.mykinopoisk.presentation.ui.extension.createWindowInsetsForToolbar
import com.example.mykinopoisk.presentation.ui.movie.adapter.NoteAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        NoteAdapter(requireContext()) { note ->
            findNavController().navigate(
                NoteFragmentDirections.toEditingNote(
                    note.id,
                    note.nameMovie,
                    note.comment
                )
            )
        }
    }

    private val viewModel by inject<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNoteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbarFragmentNote.createWindowInsetsForToolbar()
            recyclerViewNote.createWindowInsetsForRecycleView()

            toolbarFragmentNote.setOnMenuItemClickListener {
                if (it.itemId == R.id.add_note) {
                    findNavController().navigate(NoteFragmentDirections.toAddNoteFragment())
                }
                true
            }

            recyclerViewNote.adapter = adapter
            recyclerViewNote.addHorizontalSpaceDecoration(R.dimen.space_horizontal_decorator)

            viewModel
                .lce
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { lce ->
                    when (lce) {
                        is LceState.Fail -> {
                            Toast.makeText(
                                requireContext(),
                                lce.error.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        LceState.Loading -> {
                        }
                        is LceState.Success -> {
                            lce.data.collect {
                                adapter.submitList(it)

                            }
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)


            val swipeToDeleteCallback = object : SwipeElement() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val noteToDel = adapter.currentList[viewHolder.adapterPosition]

                    viewLifecycleOwner.lifecycleScope.launch {

                        showAlertDialog(noteToDel)
                    }
                }
            }
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.recyclerViewNote)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertDialog(note: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_dialog_delete_note)
            .setMessage(R.string.message_dialog_delete)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.onNoteSwipe(note)
                Toast.makeText(
                    requireContext(),
                    R.string.toast_text_delete,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                adapter.notifyDataSetChanged()

                Toast.makeText(
                    requireContext(),
                    R.string.toast_text_not_delete,
                    Toast.LENGTH_SHORT
                ).show()
                dialog.cancel()
            }
            .show()
    }
}

