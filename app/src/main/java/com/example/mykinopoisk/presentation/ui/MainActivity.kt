package com.example.mykinopoisk.presentation.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.mykinopoisk.R
import com.example.mykinopoisk.domain.model.settings.NightMode
import com.example.mykinopoisk.domain.service.LanguageService
import com.example.mykinopoisk.domain.service.NightModeService
import com.example.mykinopoisk.presentation.ui.extension.applySelectedAppLanguage
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val nightModeService by inject<NightModeService>()
    private val languageService by inject<LanguageService>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(languageService.language))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        initNightMode()

          WindowCompat.setDecorFitsSystemWindows(window, false)
          window.statusBarColor = Color.TRANSPARENT
          window.navigationBarColor = Color.GRAY
    }

    private fun initNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (nightModeService.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}
