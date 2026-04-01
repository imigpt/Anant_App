package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VerifyBankUiState(
    val firstName: String = "",
    val lastName: String = "",
    val bankName: String = "",
    val accountNumber: String = "",
    val ifscCode: String = "",
    val accountType: String = "Current", // Current or Saving
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isAlternateMode: Boolean = false
) {
    val isSubmitEnabled: Boolean
        get() = firstName.trim().isNotEmpty() &&
                lastName.trim().isNotEmpty() &&
                bankName.trim().isNotEmpty() &&
                accountNumber.trim().isNotEmpty() &&
                ifscCode.trim().length >= 11

    val title: String
        get() = if (isAlternateMode) "Alternate Bank" else "Verify Bank"
}

class VerifyBankViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyBankUiState())
    val uiState: StateFlow<VerifyBankUiState> = _uiState.asStateFlow()

    fun updateField(key: String, value: String) {
        _uiState.update { currentState ->
            when (key) {
                "firstName" -> currentState.copy(firstName = value)
                "lastName" -> currentState.copy(lastName = value)
                "bankName" -> currentState.copy(bankName = value)
                "accountNumber" -> currentState.copy(accountNumber = value.filter { it.isDigit() })
                "ifscCode" -> currentState.copy(ifscCode = value.uppercase())
                "accountType" -> currentState.copy(accountType = value)
                else -> currentState
            }
        }
    }

    fun enableAlternateBankMode() {
        _uiState.update { currentState ->
            currentState.copy(
                isAlternateMode = true,
                firstName = "",
                lastName = "",
                bankName = "",
                accountNumber = "",
                ifscCode = ""
            )
        }
    }

    fun submitBankVerification() {
        if (!_uiState.value.isSubmitEnabled) return
        
        _uiState.update { it.copy(isLoading = true) }
        
        // Simulate API call and success
        viewModelScope.launch {
            delay(1000) // Simulate network delay
            _uiState.update { 
                it.copy(
                    isLoading = false, 
                    successMessage = "Bank verification successful!" 
                ) 
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
