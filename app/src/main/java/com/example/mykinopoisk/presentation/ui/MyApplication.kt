package com.example.mykinopoisk.presentation.ui

import android.app.Application
import android.view.Window
import androidx.core.view.WindowCompat
import com.example.mykinopoisk.data.di.*
import com.example.mykinopoisk.presentation.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataModule,
                viewModelModule
            )
        }
    }
}