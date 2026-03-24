package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.anantapp.presentation.screen.QRCodeFormState

class GenerateQRCodeViewModel : ViewModel() {
    private val _formState = MutableStateFlow(QRCodeFormState())
    val formState: StateFlow<QRCodeFormState> = _formState.asStateFlow()

    fun updateFullName(fullName: String) {
        _formState.value = _formState.value.copy(fullName = fullName)
    }

    fun updateVehicleType(vehicleType: String) {
        _formState.value = _formState.value.copy(selectedVehicleType = vehicleType)
    }

    fun updateVehicleNumberPlate(plate: String) {
        _formState.value = _formState.value.copy(vehicleNumberPlate = plate)
    }

    fun updateInsurancePolicyNumber(policyNumber: String) {
        _formState.value = _formState.value.copy(insurancePolicyNumber = policyNumber)
    }

    fun updateInsuranceValidTill(validTill: String) {
        _formState.value = _formState.value.copy(insuranceValidTill = validTill)
    }

    fun updateEmergencyContactFamilyName(name: String) {
        _formState.value = _formState.value.copy(emergencyContactFamilyName = name)
    }

    fun updateEmergencyContactFamilyPhone(phone: String) {
        _formState.value = _formState.value.copy(emergencyContactFamilyPhone = phone)
    }

    fun updateEmergencyContactFriendName(name: String) {
        _formState.value = _formState.value.copy(emergencyContactFriendName = name)
    }

    fun updateEmergencyContactFriendPhone(phone: String) {
        _formState.value = _formState.value.copy(emergencyContactFriendPhone = phone)
    }

    fun toggleMedicalCondition(condition: String) {
        val currentConditions = _formState.value.selectedMedicalConditions.toMutableSet()
        if (currentConditions.contains(condition)) {
            currentConditions.remove(condition)
        } else {
            currentConditions.add(condition)
        }
        _formState.value = _formState.value.copy(selectedMedicalConditions = currentConditions)
    }

    fun updatePhotoPath(photoPath: String) {
        _formState.value = _formState.value.copy(uploadedPhotoPath = photoPath)
    }

    fun updateRCPath(rcPath: String) {
        _formState.value = _formState.value.copy(uploadedRCPath = rcPath)
    }

    fun updateInsurancePath(insurancePath: String) {
        _formState.value = _formState.value.copy(uploadedInsurancePath = insurancePath)
    }

    fun resetForm() {
        _formState.value = QRCodeFormState()
    }

    fun getFormData(): QRCodeFormState = _formState.value
}
