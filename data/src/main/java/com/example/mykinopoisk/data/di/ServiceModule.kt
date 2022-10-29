package com.example.mykinopoisk.data.di

import com.example.mykinopoisk.data.service.PreferencesService
import com.example.mykinopoisk.domain.service.LanguageService
import com.example.mykinopoisk.domain.service.NightModeService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val serviceModule = module {
    singleOf(::PreferencesService    ){
        bind<NightModeService>()
        bind<LanguageService>()
    }
}