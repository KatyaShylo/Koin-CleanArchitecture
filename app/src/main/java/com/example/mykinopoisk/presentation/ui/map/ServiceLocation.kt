package com.example.mykinopoisk.presentation.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("MissingPermission")
class ServiceLocation(context: Context) {

    private var currentLocation: Location? = null
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationFlow: Flow<Location> = callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                currentLocation = result.lastLocation
                trySend(result.lastLocation)
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 5000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationClient
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
            currentLocation = null
        }
    }

    suspend fun getCurrentLocation(): Location? = suspendCoroutine { continuation ->
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                continuation.resume(location)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}




