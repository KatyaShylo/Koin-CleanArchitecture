package com.example.mykinopoisk.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.domain.model.map.Country
import com.example.mykinopoisk.domain.repository.map.CountryRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class MapViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val countryMarkerFlow = MutableSharedFlow<Pair<Double, Double>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val countriesFlow: Flow<List<Country>> = flow {
        emit(repository.getCountries().getOrThrow())
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 1
    )

    val selectedCountryFlow = countriesFlow.combine(countryMarkerFlow) { countries, (latitude, longitude) ->
        countries.first { it.latitude == latitude && it.longitude == longitude }
    }

    fun onMarkerClicked(latitude: Double, longitude: Double) {
        countryMarkerFlow.tryEmit(latitude to longitude)
    }
}