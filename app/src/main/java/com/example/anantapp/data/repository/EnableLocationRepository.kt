package com.example.anantapp.data.repository

import com.example.anantapp.data.model.EnableLocationResult
import kotlinx.coroutines.delay

interface IEnableLocationRepository {
    suspend fun requestLocationPermission(): Boolean
    suspend fun submitLocation(latitude: Double, longitude: Double, address: String): EnableLocationResult
}

class EnableLocationRepository : IEnableLocationRepository {
    override suspend fun requestLocationPermission(): Boolean {
        delay(500)
        return true
    }

    override suspend fun submitLocation(
        latitude: Double,
        longitude: Double,
        address: String
    ): EnableLocationResult {
        return try {
            delay(2000)
            
            if (address.isNotBlank()) {
                EnableLocationResult.Success("Location enabled successfully!")
            } else {
                EnableLocationResult.Error("Please enter a valid address")
            }
        } catch (e: Exception) {
            EnableLocationResult.Error("Failed to enable location: ${e.message}")
        }
    }
}
