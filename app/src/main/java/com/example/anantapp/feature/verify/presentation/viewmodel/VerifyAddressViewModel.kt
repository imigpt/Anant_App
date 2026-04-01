package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        _uiState.update { currentState ->
            when (fieldName) {
                "homeAddress" -> currentState.copy(homeAddress = value)
                "houseFlatNumber" -> currentState.copy(houseFlatNumber = value)
                "address" -> currentState.copy(address = value)
                "city" -> currentState.copy(city = value)
                "state" -> currentState.copy(state = value)
                "pincode" -> currentState.copy(pincode = value.take(6))
                else -> currentState
            }
        }
    }

    fun submitAddressVerification() {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            delay(1000) // Simulate network delay
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    successMessage = "Address verification successful!"
                )
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
