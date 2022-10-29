package com.example.mykinopoisk.domain.model.settings

import java.util.*

enum class Language(
    val locale: Locale
) {
    EN(Locale.ENGLISH),
    RU(Locale("ru"))
}