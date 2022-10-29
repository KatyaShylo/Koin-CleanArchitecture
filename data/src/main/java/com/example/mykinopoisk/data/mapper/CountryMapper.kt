package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.model.map.CountryDTO
import com.example.mykinopoisk.data.model.map.NameDTO
import com.example.mykinopoisk.domain.model.map.Country
import com.example.mykinopoisk.domain.model.map.Name


internal fun CountryDTO.toDomainModel(): Country {
    return Country(
        name = name.toDomainModel(),
        capital = capital?.first(),
        latitude = capitalInfo.latLng?.get(0) ?: latLng[0],
        longitude = capitalInfo.latLng?.get(1) ?: latLng[1],
        population = population,
        area = area,
        flagUrl = flags.png,
        coatOfArmsUrl = coatOfArms.png
    )
}

internal fun NameDTO.toDomainModel(): Name {
    return Name(
        common = common,
        official = official
    )
}


