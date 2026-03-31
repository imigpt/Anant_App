package com.example.anantapp.feature.nominee.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class OTPVerificationResult {
    data object Idle : OTPVerificationResult()
    data object OTPSent : OTPVerificationResult()
    data object OTPVerified : OTPVerificationResult()
    data class Error(val message: String) : OTPVerificationResult()
    data object Loading : OTPVerificationResult()
}

data class OTPVerificationState(
    val phoneNumber: String = "",
    val otpCode: String = "",
    val otpSent: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

class NomineeOTPVerificationViewModel : ViewModel() {
    private val _state = MutableStateFlow(OTPVerificationState())
    val state: StateFlow<OTPVerificationState> = _state.asStateFlow()

    private val _result = MutableStateFlow<OTPVerificationResult>(OTPVerificationResult.Idle)
    val result: StateFlow<OTPVerificationResult> = _result.asStateFlow()

    fun updatePhoneNumber(phone: String) {
        // Allow only digits, limit to 10 characters
        val filteredPhone = phone.filter { it.isDigit() }.take(10)
        _state.value = _state.value.copy(phoneNumber = filteredPhone)
    }

    fun updateOTPCode(otp: String) {
        // Allow only digits, limit to 6 characters
        val filteredOTP = otp.filter { it.isDigit() }.take(6)
        _state.value = _state.value.copy(otpCode = filteredOTP)
    }

    fun sendOTP() {
        val phone = _state.value.phoneNumber.trim()

        if (phone.isEmpty()) {
            _result.value = OTPVerificationResult.Error("Please enter a phone number")
            return
        }

        if (phone.length != 10) {
            _result.value = OTPVerificationResult.Error("Phone number must be 10 digits")
            return
        }

        // Simulate OTP sending
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = ""
        )
        _result.value = OTPVerificationResult.Loading

        // Simulate network delay
        _state.value = _state.value.copy(
            isLoading = false,
            otpSent = true
        )
        _result.value = OTPVerificationResult.OTPSent
    }

    fun verifyOTP() {
        val otp = _state.value.otpCode.trim()

        if (otp.isEmpty()) {
            _result.value = OTPVerificationResult.Error("Please enter OTP")
            return
        }

        if (otp.length != 6) {
            _result.value = OTPVerificationResult.Error("OTP must be 6 digits")
            return
        }

        // Simulate OTP verification
        _state.value = _state.value.copy(isLoading = true)
        _result.value = OTPVerificationResult.Loading

        // Simulate network delay
        _state.value = _state.value.copy(isLoading = false)
        _result.value = OTPVerificationResult.OTPVerified
    }

    fun resetResult() {
        _result.value = OTPVerificationResult.Idle
    }

    fun resendOTP() {
        sendOTP()
    }

    fun goBack() {
        _state.value = OTPVerificationState()
        _result.value = OTPVerificationResult.Idle
    }
}
