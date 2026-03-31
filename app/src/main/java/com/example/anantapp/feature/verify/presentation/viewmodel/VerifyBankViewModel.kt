package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VerifyBankUiState(
    val accountType: String? = null,
    val accountHolderName: String = "",
    val accountNumber: String = "",
    val ifscCode: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class VerifyBankResult {
    object Idle : VerifyBankResult()
    data class Success(val message: String) : VerifyBankResult()
    data class Error(val message: String) : VerifyBankResult()
}

class VerifyBankViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyBankUiState())
    val uiState: StateFlow<VerifyBankUiState> = _uiState.asStateFlow()

    fun selectAccountType(type: String) {
        _uiState.value = _uiState.value.copy(accountType = type)
    }

    fun updateAccountHolderName(name: String) {
        _uiState.value = _uiState.value.copy(accountHolderName = name)
    }

    fun updateAccountNumber(number: String) {
        _uiState.value = _uiState.value.copy(accountNumber = number)
    }

    fun updateIfscCode(code: String) {
        _uiState.value = _uiState.value.copy(ifscCode = code.uppercase())
    }

    fun submitBankVerification() {
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
