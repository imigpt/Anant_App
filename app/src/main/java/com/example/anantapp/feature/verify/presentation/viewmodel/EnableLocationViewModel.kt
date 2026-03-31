package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EnableLocationUiState(
    val address: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isLocationEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

sealed class EnableLocationResult {
    object Idle : EnableLocationResult()
    data class Success(val message: String) : EnableLocationResult()
    data class Error(val message: String) : EnableLocationResult()
}

class EnableLocationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EnableLocationUiState())
    val uiState: StateFlow<EnableLocationUiState> = _uiState.asStateFlow()

    fun updateAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun enableLocationServices() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Location permission request would go here
        _uiState.value = _uiState.value.copy(
            isLocationEnabled = true,
            isLoading = false,
            successMessage = "Location enabled successfully"
        )
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            error = null
        )
    }
}
