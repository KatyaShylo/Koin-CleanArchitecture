package com.example.mykinopoisk.presentation.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentSettingsBinding
import com.example.mykinopoisk.domain.model.settings.Language
import com.example.mykinopoisk.domain.model.settings.NightMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbarFragmentSettings.setNavigationOnClickListener {
                findNavController().navigate(R.id.fragment_favorites)
            }


            when (viewModel.selectedNightMode) {
                NightMode.SYSTEM -> buttonSystemMode
                NightMode.DARK -> buttonDarkMode
                NightMode.LIGHT -> buttonLightMode
            }.isChecked = true

            radioGroupNightMode.setOnCheckedChangeListener { _, buttonId ->
                val (prefsMode, systemMode) = when (buttonId) {
                    buttonDarkMode.id -> NightMode.DARK to AppCompatDelegate.MODE_NIGHT_YES
                    buttonLightMode.id -> NightMode.LIGHT to AppCompatDelegate.MODE_NIGHT_NO
                    buttonSystemMode.id -> NightMode.SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> error("incorrect button ID")
                }

                viewModel.selectedNightMode = prefsMode
                AppCompatDelegate.setDefaultNightMode(systemMode)
            }


            when (viewModel.selectedLanguage) {
                Language.EN -> buttonLanguageEn
                Language.RU -> buttonLanguageRu
            }.isChecked = true

            radioGroupLanguage.setOnCheckedChangeListener { _, buttonId ->
                val language = when (buttonId) {
                    buttonLanguageEn.id -> Language.EN
                    buttonLanguageRu.id -> Language.RU
                    else -> error("incorrect buttonId")
                }
                viewModel.selectedLanguage = language

                activity?.recreate()
            }
        }

        createWindowInsets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarFragmentSettings) { _, insets ->
            val systemBarInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbarFragmentSettings.updatePadding(
                top = systemBarInset.top,
                left = systemBarInset.left,
                right = systemBarInset.right
            )
            insets
        }
    }
}