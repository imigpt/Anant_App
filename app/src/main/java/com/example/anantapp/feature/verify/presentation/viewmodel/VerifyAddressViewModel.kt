package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VerifyAddressUiState(
    val homeAddress: String = "",
    val houseFlatNumber: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class VerifyAddressResult {
    object Idle : VerifyAddressResult()
    data class Success(val message: String) : VerifyAddressResult()
    data class Error(val message: String) : VerifyAddressResult()
}

class VerifyAddressViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyAddressUiState())
    val uiState: StateFlow<VerifyAddressUiState> = _uiState.asStateFlow()

    fun updateField(fieldName: String, value: String) {
        _uiState.value = when (fieldName) {
            "homeAddress" -> _uiState.value.copy(homeAddress = value)
            "houseFlatNumber" -> _uiState.value.copy(houseFlatNumber = value)
            "address" -> _uiState.value.copy(address = value)
            "city" -> _uiState.value.copy(city = value)
            "state" -> _uiState.value.copy(state = value)
            "pincode" -> _uiState.value.copy(pincode = value.take(6))
            else -> _uiState.value
        }
    }

    fun submitAddressVerification() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
