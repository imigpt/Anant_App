package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DeliveryAddressUiState(
    val deliveryName: String = "",
    val houseNumber: String = "",
    val streetLocality: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val mobileNumber: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class DeliveryAddressViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DeliveryAddressUiState())
    val uiState: StateFlow<DeliveryAddressUiState> = _uiState.asStateFlow()

    fun updateDeliveryName(name: String) {
        _uiState.value = _uiState.value.copy(deliveryName = name)
    }

    fun updateHouseNumber(number: String) {
        _uiState.value = _uiState.value.copy(houseNumber = number)
    }

    fun updateStreetLocality(street: String) {
        _uiState.value = _uiState.value.copy(streetLocality = street)
    }

    fun updateCity(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }

    fun updateState(state: String) {
        _uiState.value = _uiState.value.copy(state = state)
    }

    fun updatePincode(pincode: String) {
        _uiState.value = _uiState.value.copy(pincode = pincode)
    }

    fun updateMobileNumber(number: String) {
        _uiState.value = _uiState.value.copy(mobileNumber = number)
    }
}
