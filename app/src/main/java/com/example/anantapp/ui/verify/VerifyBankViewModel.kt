package com.example.anantapp.ui.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.VerifyBankResult
import com.example.anantapp.data.model.VerifyBankState
import com.example.anantapp.data.repository.VerifyBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Bank Verification Screen
 */
class VerifyBankViewModel(
    private val verifyBankRepository: VerifyBankRepository = VerifyBankRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyBankState())
    val uiState: StateFlow<VerifyBankState> = _uiState.asStateFlow()

    /**
     * Update generic field based on key
     */
    fun updateField(key: String, value: String) {
        when (key) {
            "firstName" -> updateFirstName(value)
            "lastName" -> updateLastName(value)
            "birthName" -> updateBirthName(value)
            "bankName" -> updateBankName(value)
            "accountNumber" -> updateAccountNumber(value)
            "ifscCode" -> updateIfscCode(value)
            "accountType" -> {
                _uiState.update { it.copy(accountType = value) }
            }
        }
    }

    /**
     * Update first name
     */
    fun updateFirstName(firstName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                firstName = firstName,
                isFirstNameValid = firstName.isNotBlank() && firstName.length >= 2
            )
        }
    }

    /**
     * Update last name
     */
    fun updateLastName(lastName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName,
                isLastNameValid = lastName.isNotBlank() && lastName.length >= 2
            )
        }
    }

    /**
     * Update birth name
     */
    fun updateBirthName(birthName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                birthName = birthName,
                isBirthNameValid = birthName.isNotBlank() && birthName.length >= 2
            )
        }
    }

    /**
     * Update bank name
     */
    fun updateBankName(bankName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                bankName = bankName,
                isBankNameValid = bankName.isNotBlank() && bankName.length >= 2
            )
        }
    }

    /**
     * Update account number
     */
    fun updateAccountNumber(accountNumber: String) {
        _uiState.update { currentState ->
            val filtered = accountNumber.filter { it.isDigit() }
            currentState.copy(
                accountNumber = filtered,
                isAccountNumberValid = filtered.length in 10..18
            )
        }
    }

    /**
     * Update IFSC code
     */
    fun updateIfscCode(ifscCode: String) {
        _uiState.update { currentState ->
            val upper = ifscCode.uppercase()
            currentState.copy(
                ifscCode = upper,
                isIfscCodeValid = upper.length == 11
            )
        }
    }

    /**
     * Toggle account type between Current and Saving
     */
    fun toggleAccountType() {
        _uiState.update { currentState ->
            currentState.copy(
                accountType = if (currentState.accountType == "Current") "Saving" else "Current"
            )
        }
    }

    /**
     * Enable alternate bank mode and clear fields
     */
    fun enableAlternateBankMode() {
        _uiState.update { 
            VerifyBankState(isAlternateMode = true)
        }
    }

    /**
     * Submit bank verification
     */
    fun submitBankVerification() {
        if (!_uiState.value.isSubmitEnabled) {
            _uiState.update { it.copy(errorMessage = "Please fill all fields correctly") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentState = _uiState.value
            val result = verifyBankRepository.submitBankVerification(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                birthName = currentState.birthName,
                accountNumber = currentState.accountNumber,
                ifscCode = currentState.ifscCode,
                accountType = currentState.accountType
            )

            handleVerifyBankResult(result)
        }
    }

    /**
     * Handle verification results
     */
    private fun handleVerifyBankResult(result: VerifyBankResult) {
        when (result) {
            is VerifyBankResult.Success -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        successMessage = result.message
                    )
                }
            }
            is VerifyBankResult.Error -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
            VerifyBankResult.Loading -> {
                _uiState.update { it.copy(isLoading = true) }
            }
        }
    }

    /**
     * Clear messages
     */
    fun clearMessages() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }
}
