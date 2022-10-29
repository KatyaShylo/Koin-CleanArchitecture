package com.example.mykinopoisk.presentation.ui.setting

import androidx.lifecycle.ViewModel
import com.example.mykinopoisk.domain.model.settings.Language
import com.example.mykinopoisk.domain.service.LanguageService
import com.example.mykinopoisk.domain.service.NightModeService

class SettingsViewModel(
    private val prefsService: NightModeService,
    private val languageService: LanguageService
) : ViewModel() {

    var selectedNightMode by prefsService::nightMode

    var selectedLanguage: Language by languageService::language
}