package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DonorScreenUiState(
    val selectedDonorType: String? = null,
    val donationAmount: String = "",
    val panNumber: String = "",
    val aadharNumber: String = "",
    val companyName: String = "",
    val companyRegistration: String = "",
    val taxId: String = "",
    val employeeId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class DonorScreenResult {
    object Idle : DonorScreenResult()
    data class Success(val donorId: String) : DonorScreenResult()
    data class Error(val message: String) : DonorScreenResult()
}

class DonorScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DonorScreenUiState())
    val uiState: StateFlow<DonorScreenUiState> = _uiState.asStateFlow()

    fun selectDonorType(type: String) {
        _uiState.value = _uiState.value.copy(selectedDonorType = type)
    }

    fun updateDonationAmount(amount: String) {
        _uiState.value = _uiState.value.copy(donationAmount = amount)
    }

    fun updatePanNumber(pan: String) {
        _uiState.value = _uiState.value.copy(panNumber = pan)
    }

    fun updateAadharNumber(aadhar: String) {
        _uiState.value = _uiState.value.copy(aadharNumber = aadhar)
    }

    fun updateCompanyName(name: String) {
        _uiState.value = _uiState.value.copy(companyName = name)
    }

    fun updateCompanyRegistration(registration: String) {
        _uiState.value = _uiState.value.copy(companyRegistration = registration)
    }

    fun updateTaxId(taxId: String) {
        _uiState.value = _uiState.value.copy(taxId = taxId)
    }

    fun updateEmployeeId(employeeId: String) {
        _uiState.value = _uiState.value.copy(employeeId = employeeId)
    }

    fun proceedToDonation() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
    }
}
