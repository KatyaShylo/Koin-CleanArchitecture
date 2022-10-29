package com.example.mykinopoisk.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(
        databaseModule,
        networkModule,
        repositoryModule,
        serviceModule
    )
}