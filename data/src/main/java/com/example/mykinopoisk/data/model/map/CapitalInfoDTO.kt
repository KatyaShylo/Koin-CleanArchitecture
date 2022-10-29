package com.example.mykinopoisk.data.model.map

import com.google.gson.annotations.SerializedName

internal data class CapitalInfoDTO(
    @SerializedName("latlng")
    val latLng: List<Double>? = null
)