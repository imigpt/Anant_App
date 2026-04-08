package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DonorScreenViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(DonorScreenState())
    val uiState: StateFlow<DonorScreenState> = _uiState.asStateFlow()

    fun updateDonorType(type: String) {
        _uiState.value = _uiState.value.copy(donorType = type)
    }

    fun updateFullName(name: String) {
        _uiState.value = _uiState.value.copy(fullName = name)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updateMobileNumber(number: String) {
        _uiState.value = _uiState.value.copy(mobileNumber = number)
    }

    fun updatePanNumber(pan: String) {
        _uiState.value = _uiState.value.copy(panNumber = pan)
    }

    fun updateHouseNo(houseNo: String) {
        _uiState.value = _uiState.value.copy(houseNo = houseNo)
    }

    fun updateStreet(street: String) {
        _uiState.value = _uiState.value.copy(street = street)
    }

    fun updateState(state: String) {
        _uiState.value = _uiState.value.copy(state = state)
    }

    fun updateCity(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }

    fun updatePincode(pincode: String) {
        _uiState.value = _uiState.value.copy(pincode = pincode)
    }

    fun updateEmployeeId(id: String) {
        _uiState.value = _uiState.value.copy(employeeId = id)
    }

    fun updateDepartment(dept: String) {
        _uiState.value = _uiState.value.copy(department = dept)
    }

    fun updateDesignation(designation: String) {
        _uiState.value = _uiState.value.copy(designation = designation)
    }

    fun updateCompanyName(companyName: String) {
        _uiState.value = _uiState.value.copy(companyName = companyName)
    }

    fun updateGstNumber(gstNumber: String) {
        _uiState.value = _uiState.value.copy(gstNumber = gstNumber)
    }

    fun updateContactPersonName(contactPersonName: String) {
        _uiState.value = _uiState.value.copy(contactPersonName = contactPersonName)
    }

    fun updateOfficialEmail(officialEmail: String) {
        _uiState.value = _uiState.value.copy(officialEmail = officialEmail)
    }

    fun updateDonationAmount(amount: String) {
        _uiState.value = _uiState.value.copy(donationAmount = amount, selectedQuickAmount = "")
    }

    fun selectQuickAmount(amount: String) {
        _uiState.value = _uiState.value.copy(donationAmount = amount, selectedQuickAmount = amount)
    }

    fun submitDonation() {
        // TODO: Implement donation submission logic
    }
}
