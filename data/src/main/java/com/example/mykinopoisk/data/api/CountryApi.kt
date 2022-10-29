package com.example.mykinopoisk.data.api

import com.example.mykinopoisk.data.model.map.CountryDTO
import retrofit2.http.GET

internal interface CountryApi {

    @GET("all")
    suspend fun getCountries(): List<CountryDTO>
}