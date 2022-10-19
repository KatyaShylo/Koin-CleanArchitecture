package com.example.mykinopoisk.presentation.ui

import android.app.Application
import com.example.mykinopoisk.data.di.databaseModule
import com.example.mykinopoisk.data.di.networkModule
import com.example.mykinopoisk.data.di.repositoryModule
import com.example.mykinopoisk.data.di.useCaseModule
import com.example.mykinopoisk.presentation.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}