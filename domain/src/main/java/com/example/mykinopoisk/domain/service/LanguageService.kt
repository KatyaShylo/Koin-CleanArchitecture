package com.example.mykinopoisk.domain.service

import com.example.mykinopoisk.domain.model.settings.Language
import kotlinx.coroutines.flow.Flow

interface LanguageService {
    var language: Language
    val languageFlow: Flow<Language>
}