package com.example.mykinopoisk.data.di

import com.example.mykinopoisk.data.api.CountryApi
import com.example.mykinopoisk.data.api.KinopoiskApi
import com.example.mykinopoisk.data.model.retrofit.RetrofitType
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.kinopoisk.dev/"
private const val BASE_URL_COUNTRY = "https://restcountries.com/v3.1/"

internal val networkModule = module {

    single(named(RetrofitType.API_KINOPOISK)){
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single { get<Retrofit>(named(RetrofitType.API_KINOPOISK)).create<KinopoiskApi>()}


    single(named(RetrofitType.API_COUNTRY)){
        Retrofit.Builder()
            .baseUrl(BASE_URL_COUNTRY)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single { get<Retrofit>(named(RetrofitType.API_COUNTRY)).create<CountryApi>()}
}