package com.example.mykinopoisk.domain.repository.map

import com.example.mykinopoisk.domain.model.map.Country

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
}

