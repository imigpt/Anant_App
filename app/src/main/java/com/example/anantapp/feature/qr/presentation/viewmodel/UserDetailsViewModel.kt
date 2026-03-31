package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserDetailsUiState(
    val userName: String = "",
    val userEmail: String = "",
    val userPhone: String = "",
    val bloodType: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val address: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    fun updateUserName(name: String) {
        _uiState.value = _uiState.value.copy(userName = name)
    }

    fun updateUserEmail(email: String) {
        _uiState.value = _uiState.value.copy(userEmail = email)
    }

    fun updateUserPhone(phone: String) {
        _uiState.value = _uiState.value.copy(userPhone = phone)
    }

    fun updateBloodType(type: String) {
        _uiState.value = _uiState.value.copy(bloodType = type)
    }

    fun updateEmergencyContactName(name: String) {
        _uiState.value = _uiState.value.copy(emergencyContactName = name)
    }

    fun updateEmergencyContactPhone(phone: String) {
        _uiState.value = _uiState.value.copy(emergencyContactPhone = phone)
    }

    fun updateAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }
}
