package com.example.anantapp.data.model

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
