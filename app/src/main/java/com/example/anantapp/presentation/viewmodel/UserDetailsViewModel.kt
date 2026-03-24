package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.anantapp.presentation.screen.UserDetailsFormState

class UserDetailsViewModel : ViewModel() {
    private val _formState = MutableStateFlow(UserDetailsFormState())
    val formState: StateFlow<UserDetailsFormState> = _formState.asStateFlow()

    fun updateUserName(userName: String) {
        _formState.value = _formState.value.copy(userName = userName)
    }

    fun updateUserPhoto(photoPath: String) {
        _formState.value = _formState.value.copy(userPhotoPath = photoPath)
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

    fun updateRCDocument(rcPath: String) {
        _formState.value = _formState.value.copy(rcDocumentPath = rcPath)
    }

    fun updateInsuranceDocument(insurancePath: String) {
        _formState.value = _formState.value.copy(insuranceDocumentPath = insurancePath)
    }

    fun updateEmergencyContactName(name: String) {
        _formState.value = _formState.value.copy(emergencyContactName = name)
    }

    fun updateEmergencyContactPhone(phone: String) {
        _formState.value = _formState.value.copy(emergencyContactPhone = phone)
    }

    fun updateEmergencyContactAddress1(address: String) {
        _formState.value = _formState.value.copy(emergencyContactAddress1 = address)
    }

    fun updateEmergencyContactAddress2(address: String) {
        _formState.value = _formState.value.copy(emergencyContactAddress2 = address)
    }

    fun updateBloodType(bloodType: String) {
        _formState.value = _formState.value.copy(bloodType = bloodType)
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

    fun resetForm() {
        _formState.value = UserDetailsFormState()
    }

    fun getFormData(): UserDetailsFormState = _formState.value
}
