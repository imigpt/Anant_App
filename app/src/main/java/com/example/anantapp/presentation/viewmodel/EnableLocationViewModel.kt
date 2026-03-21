package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.EnableLocationResult
import com.example.anantapp.data.model.EnableLocationState
import com.example.anantapp.data.repository.EnableLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EnableLocationViewModel : ViewModel() {
    private val repository = EnableLocationRepository()
    
    private val _state = MutableStateFlow(
        EnableLocationState(
            latitude = null,
            longitude = null,
            address = "",
            isLocationEnabled = false,
            isLoading = false,
            successMessage = null,
            errorMessage = null,
            title = "Enable Precise Location"
        )
    )
    val state: StateFlow<EnableLocationState> = _state.asStateFlow()
    
    fun updateAddress(address: String) {
        _state.value = _state.value.copy(
            address = address
        )
    }
    
    fun setLocationData(latitude: Double, longitude: Double, address: String) {
        _state.value = _state.value.copy(
            latitude = latitude,
            longitude = longitude,
            address = address,
            isLocationEnabled = true
        )
    }
    
    fun enableLocationServices() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val permissionGranted = repository.requestLocationPermission()
            if (permissionGranted) {
                _state.value = _state.value.copy(
                    isLocationEnabled = true,
                    isLoading = false
                )
            } else {
                _state.value = _state.value.copy(
                    isLocationEnabled = false,
                    errorMessage = "Location permission denied",
                    isLoading = false
                )
            }
        }
    }
    
    fun submitLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            val result = repository.submitLocation(
                latitude = _state.value.latitude ?: 0.0,
                longitude = _state.value.longitude ?: 0.0,
                address = _state.value.address
            )
            
            when (result) {
                is EnableLocationResult.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = result.message,
                        isLoading = false
                    )
                }
                is EnableLocationResult.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = result.error,
                        isLoading = false
                    )
                }
                is EnableLocationResult.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
