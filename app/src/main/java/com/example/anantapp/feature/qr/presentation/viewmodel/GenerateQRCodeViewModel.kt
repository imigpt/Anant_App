package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GenerateQRCodeUiState(
    val fullName: String = "",
    val selectedVehicleType: String? = null,
    val vehicleNumberPlate: String = "",
    val insurancePolicyNumber: String = "",
    val insuranceValidTill: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val uploadedPhotoPath: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class GenerateQRCodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GenerateQRCodeUiState())
    val uiState: StateFlow<GenerateQRCodeUiState> = _uiState.asStateFlow()

    fun updateFullName(name: String) {
        _uiState.value = _uiState.value.copy(fullName = name)
    }

    fun updateVehicleType(type: String) {
        _uiState.value = _uiState.value.copy(selectedVehicleType = type)
    }

    fun updateVehicleNumberPlate(plate: String) {
        _uiState.value = _uiState.value.copy(vehicleNumberPlate = plate)
    }

    fun updateInsurancePolicyNumber(number: String) {
        _uiState.value = _uiState.value.copy(insurancePolicyNumber = number)
    }

    fun updateInsuranceValidTill(date: String) {
        _uiState.value = _uiState.value.copy(insuranceValidTill = date)
    }

    fun updateEmergencyContactName(name: String) {
        _uiState.value = _uiState.value.copy(emergencyContactName = name)
    }

    fun updateEmergencyContactPhone(phone: String) {
        _uiState.value = _uiState.value.copy(emergencyContactPhone = phone)
    }

    fun updatePhotoPath(path: String) {
        _uiState.value = _uiState.value.copy(uploadedPhotoPath = path)
    }
}
