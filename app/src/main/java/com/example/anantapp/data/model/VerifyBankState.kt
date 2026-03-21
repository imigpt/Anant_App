package com.example.anantapp.data.model

/**
 * UI State for Bank Verification Screen
 */
data class VerifyBankState(
    val firstName: String = "",
    val lastName: String = "",
    val birthName: String = "",
    val bankName: String = "",
    val accountNumber: String = "",
    val ifscCode: String = "",
    val accountType: String = "Current", // Current or Saving
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isFirstNameValid: Boolean = false,
    val isLastNameValid: Boolean = false,
    val isBirthNameValid: Boolean = false,
    val isBankNameValid: Boolean = false,
    val isAccountNumberValid: Boolean = false,
    val isIfscCodeValid: Boolean = false,
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

/**
 * Sealed class for bank verification results
 */
sealed class VerifyBankResult {
    object Loading : VerifyBankResult()
    data class Success(val message: String) : VerifyBankResult()
    data class Error(val message: String) : VerifyBankResult()
}
