package com.example.mykinopoisk.domain.model.map

data class Country(
    val name: Name,
    val capital: String? = null,
    val latitude: Double,
    val longitude: Double,
    val population: Long,
    val area: Double,
    val flagUrl: String?,
    val coatOfArmsUrl: String?
)


