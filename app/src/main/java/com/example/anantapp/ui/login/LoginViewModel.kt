package com.example.anantapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.LoginResult
import com.example.anantapp.data.model.LoginUiState
import com.example.anantapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = phoneNumber,
                isPhoneValid = isValidPhoneNumber(phoneNumber),
                errorMessage = null
            )
        }
    }

    fun onOtpChange(otp: String) {
        _uiState.update { currentState ->
            currentState.copy(
                otp = otp,
                isOtpValid = isValidOtp(otp),
                errorMessage = null
            )
        }
    }

    fun requestOtp() {
        if (!_uiState.value.isPhoneValid) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid phone number") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.requestOtp(_uiState.value.phoneNumber)

            when (result) {
                is LoginResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            // showOtpField = true (Kept here in case your Data Class requires it, 
                            // though the new UI renders both fields immediately)
                            showOtpField = true, 
                            successMessage = result.message
                        )
                    }
                }
                is LoginResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                LoginResult.Loading -> { } // Loading handled above
            }
        }
    }

    fun verifyOtp() {
        if (!_uiState.value.isOtpValid) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid OTP") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.verifyOtp(
                _uiState.value.phoneNumber,
                _uiState.value.otp
            )

            when (result) {
                is LoginResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            successMessage = result.message
                        )
                    }
                }
                is LoginResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                LoginResult.Loading -> { } // Loading handled above
            }
        }
    }

    fun loginWithApple() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.loginWithApple()

            when (result) {
                is LoginResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            successMessage = result.message
                        )
                    }
                }
                is LoginResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                LoginResult.Loading -> { } // Loading handled above
            }
        }
    }

    fun clearMessages() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }
    }

    private fun isValidOtp(otp: String): Boolean {
        return otp.length == 6 && otp.all { it.isDigit() }
    }
}