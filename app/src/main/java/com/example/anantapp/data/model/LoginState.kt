package com.example.anantapp.data.model

/**
 * UI State for Login Screen
 */
data class LoginUiState(
    val phoneNumber: String = "",
    val otp: String = "",
    val isPhoneValid: Boolean = false,
    val isOtpValid: Boolean = false,
    val showOtpField: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
)

/**
 * Request data for OTP generation
 */
data class OtpRequest(
    val phoneNumber: String,
)

/**
 * Request data for OTP verification
 */
data class OtpVerificationRequest(
    val phoneNumber: String,
    val otp: String,
)

/**
 * Response from login operations
 */
sealed class LoginResult {
    object Loading : LoginResult()
    data class Success(val message: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
