package com.example.anantapp.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.anantapp.data.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LoginResult {
    data object Idle : LoginResult()
    data object LoginSuccess : LoginResult()
    data object OTPSent : LoginResult()
    data class Error(val message: String) : LoginResult()
    data object Loading : LoginResult()
}

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val result: StateFlow<LoginResult> = _result.asStateFlow()

    fun onPhoneNumberChange(phoneNumber: String) {
        // Filter to digits only, max 10 characters
        val filteredPhone = phoneNumber.filter { it.isDigit() }.take(10)
        _uiState.value = _uiState.value.copy(phoneNumber = filteredPhone)
    }

    fun onOtpChange(otp: String) {
        // Filter to digits only, max 6 characters
        val filteredOtp = otp.filter { it.isDigit() }.take(6)
        _uiState.value = _uiState.value.copy(otp = filteredOtp)
    }

    fun requestOtp() {
        val phone = _uiState.value.phoneNumber.trim()

        if (phone.isEmpty()) {
            _result.value = LoginResult.Error("Please enter a phone number")
            return
        }

        if (phone.length != 10) {
            _result.value = LoginResult.Error("Phone number must be 10 digits")
            return
        }

        // Simulate OTP request
        _uiState.value = _uiState.value.copy(isLoading = true)
        _result.value = LoginResult.Loading

        // Simulate network delay
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            otpSent = true
        )
        _result.value = LoginResult.OTPSent
    }

    fun login() {
        val phone = _uiState.value.phoneNumber.trim()
        val otp = _uiState.value.otp.trim()

        if (phone.isEmpty()) {
            _result.value = LoginResult.Error("Please enter a phone number")
            return
        }

        if (phone.length != 10) {
            _result.value = LoginResult.Error("Phone number must be 10 digits")
            return
        }

        if (otp.isEmpty()) {
            _result.value = LoginResult.Error("Please enter OTP")
            return
        }

        if (otp.length != 6) {
            _result.value = LoginResult.Error("OTP must be 6 digits")
            return
        }

        // Simulate login
        _uiState.value = _uiState.value.copy(isLoading = true)
        _result.value = LoginResult.Loading

        // Simulate network delay
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            successMessage = "Login successful!",
            phoneNumber = "",
            otp = "",
            otpSent = false
        )
        _result.value = LoginResult.LoginSuccess
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
        _result.value = LoginResult.Idle
    }

    fun resetResult() {
        _result.value = LoginResult.Idle
    }
}
