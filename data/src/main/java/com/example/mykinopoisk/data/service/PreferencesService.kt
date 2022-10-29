package com.example.mykinopoisk.data.service

import android.content.Context
import com.example.mykinopoisk.domain.model.settings.Language
import com.example.mykinopoisk.domain.model.settings.NightMode
import com.example.mykinopoisk.domain.service.LanguageService
import com.example.mykinopoisk.domain.service.NightModeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreferencesService(
    context: Context
) : NightModeService, LanguageService {

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var nightMode: NightMode by enumPref(KEY_DARK_MODE, NightMode.SYSTEM)

    override var language: Language by PrefsDelegate(
        sharedPreferences,
        getValue = {
            getString(KEY_LANGUAGE, null)
                ?.let { enumValueOf<Language>(it) }
                ?: Language.EN
        },
        setValue = {
            putString(KEY_LANGUAGE, it.name)
            _languageFlow.tryEmit(it)
        }
    )

    private val _languageFlow = MutableStateFlow(language)
    override val languageFlow: Flow<Language> = _languageFlow.asStateFlow()

    private inline fun <reified E : Enum<E>> enumPref(key: String, defaultValue: E) =
        PrefsDelegate(
            sharedPreferences,
            getValue = { getString(key, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(key, it.name) }
        )

    companion object {
        private const val PREFS_NAME = "setting"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_LANGUAGE = "language"
    }
}