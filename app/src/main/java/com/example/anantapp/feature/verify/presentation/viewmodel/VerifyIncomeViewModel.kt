package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VerifyIncomeUiState(
    val grossSalary: String = "",
    val netSalary: String = "",
    val accountNumber: String = "",
    val ifscCode: String = "",
    val nomineeName: String = "",
    val nomineeDob: String = "",
    val nomineeAccountNumber: String = "",
    val nomineeIfscCode: String = "",
    val shareOfFunds: String = "100",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val currentStep: Int = 1 // 1 for income, 2 for nominee
)

sealed class VerifyIncomeResult {
    object Idle : VerifyIncomeResult()
    data class Success(val message: String) : VerifyIncomeResult()
    data class Error(val message: String) : VerifyIncomeResult()
}

class VerifyIncomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyIncomeUiState())
    val uiState: StateFlow<VerifyIncomeUiState> = _uiState.asStateFlow()

    fun updateIncomeField(fieldName: String, value: String) {
        _uiState.value = when (fieldName) {
            "grossSalary" -> _uiState.value.copy(grossSalary = value)
            "netSalary" -> _uiState.value.copy(netSalary = value)
            "accountNumber" -> _uiState.value.copy(accountNumber = value)
            "ifscCode" -> _uiState.value.copy(ifscCode = value.uppercase())
            else -> _uiState.value
        }
    }

    fun updateNomineeField(fieldName: String, value: String) {
        _uiState.value = when (fieldName) {
            "nomineeName" -> _uiState.value.copy(nomineeName = value)
            "nomineeDob" -> _uiState.value.copy(nomineeDob = value)
            "nomineeAccountNumber" -> _uiState.value.copy(nomineeAccountNumber = value)
            "nomineeIfscCode" -> _uiState.value.copy(nomineeIfscCode = value.uppercase())
            "shareOfFunds" -> _uiState.value.copy(shareOfFunds = value)
            else -> _uiState.value
        }
    }

    fun proceedToNominee() {
        _uiState.value = _uiState.value.copy(currentStep = 2)
    }

    fun backToIncome() {
        _uiState.value = _uiState.value.copy(currentStep = 1)
    }

    fun submitIncomeVerification() {
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
