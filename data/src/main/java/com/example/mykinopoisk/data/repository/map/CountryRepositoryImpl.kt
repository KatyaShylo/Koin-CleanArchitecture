package com.example.mykinopoisk.data.repository.map

import com.example.mykinopoisk.data.api.CountryApi
import com.example.mykinopoisk.data.mapper.toDomainModel
import com.example.mykinopoisk.domain.model.map.Country
import com.example.mykinopoisk.domain.repository.map.CountryRepository

internal class CountryRepositoryImpl(
    private val countryApi: CountryApi
) : CountryRepository {
    override suspend fun getCountries(): Result<List<Country>> = runCatching {
        countryApi.getCountries()
    }.map {
        it.map { countryDTO ->
            countryDTO.toDomainModel()
        }
    }
}