package com.example.anantapp.data.model

data class LoginUiState(
    val phoneNumber: String = "",
    val otp: String = "",
    val isPhoneValid: Boolean = false,
    val isOtpValid: Boolean = false,
    val showOtpField: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val otpSent: Boolean = false
)
