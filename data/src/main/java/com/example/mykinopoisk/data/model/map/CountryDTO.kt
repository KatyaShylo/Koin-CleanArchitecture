package com.example.mykinopoisk.data.model.map

import com.google.gson.annotations.SerializedName

internal data class CountryDTO(
   val name : NameDTO,
   val capital: List<String>? = null,
   @SerializedName("latlng")
   val latLng: List<Double>,
   val population: Long,
   val area: Double,
   val flags: FlagsDTO,
   val coatOfArms: CoatOfArmsDTO,
   val capitalInfo: CapitalInfoDTO
)
