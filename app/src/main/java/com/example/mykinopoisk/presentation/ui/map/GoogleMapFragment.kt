package com.example.mykinopoisk.presentation.ui.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.FragmentMapBinding
import com.example.mykinopoisk.presentation.ui.extension.createWindowInsetsForGoogleMap
import com.example.mykinopoisk.presentation.ui.extension.doOnApplyWindowInsets
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class GoogleMapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<MapViewModel>()

    private var googleMap: GoogleMap? = null

    private val locationService by lazy {
        ServiceLocation(requireContext())
    }
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isEnabled ->
        setEnableLocation(isEnabled)
        if (isEnabled) {
            viewLifecycleOwner.lifecycleScope.launch {
                locationService.getCurrentLocation() ?: return@launch
            }

            locationService
                .locationFlow
                .onEach { location ->
                    locationListener?.onLocationChanged(location)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

        with(binding) {

            googleMapView.createWindowInsetsForGoogleMap()


            googleMapView.getMapAsync { map ->

                googleMap = map.apply {
                    uiSettings.isCompassEnabled = true
                    uiSettings.isZoomControlsEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true

                    isMyLocationEnabled = hasLocationPermission()

                    setLocationSource(object : LocationSource {
                        override fun activate(listener: LocationSource.OnLocationChangedListener) {
                            locationListener = listener
                        }

                        override fun deactivate() {
                            locationListener = null
                        }
                    })

                    map.setOnMarkerClickListener { marker ->
                        viewModel.onMarkerClicked(
                            marker.position.latitude,
                            marker.position.longitude
                        )
                        true
                    }

                    map.setOnMapClickListener {
                        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }
                viewModel.countriesFlow
                    .onEach { countries ->
                        countries.forEach { country ->
                            map.addMarker(
                                MarkerOptions()
                                    .title(country.name.toString())
                                    .position(LatLng(country.latitude, country.longitude))
                            )
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)

                viewModel
                    .selectedCountryFlow
                    .onEach { country ->
                        with(bottomSheetContent) {
                            textNameCommon.text = country.name.common
                            textNameOfficial.text = country.name.official
                            imageFlag.load(country.flagUrl)
                            textCity.text = country.capital
                            textPopulation.text =
                                "${context?.resources?.getString(R.string.title_population)} ${country.population}"
                            textArea.text =
                                "${context?.resources?.getString(R.string.title_area)} ${country.area}"
                        }
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
            googleMapView.onCreate(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.googleMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.googleMapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.googleMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.googleMapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.googleMapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.googleMapView.onDestroy()
        googleMap = null
        _binding = null
    }

    @SuppressLint("MissingPermission")
    private fun setEnableLocation(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}