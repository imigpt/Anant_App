package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Production-level ViewModel for Payment Method Screen
 * Manages payment method selection and card details
 */
data class PaymentMethodState(
    val selectedPaymentMethod: String = "googleplay",
    val cardHolderName: String = "",
    val cardNumber: String = "",
    val cardExpiry: String = "",
    val cardCVV: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PaymentMethodViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(PaymentMethodState())
    val uiState: StateFlow<PaymentMethodState> = _uiState.asStateFlow()

    fun updatePaymentMethod(method: String) {
        _uiState.value = _uiState.value.copy(selectedPaymentMethod = method)
    }

    fun updateCardHolderName(name: String) {
        _uiState.value = _uiState.value.copy(cardHolderName = name)
    }

    fun updateCardNumber(number: String) {
        _uiState.value = _uiState.value.copy(cardNumber = number)
    }

    fun updateCardExpiry(expiry: String) {
        _uiState.value = _uiState.value.copy(cardExpiry = expiry)
    }

    fun updateCardCVV(cvv: String) {
        _uiState.value = _uiState.value.copy(cardCVV = cvv)
    }

    fun processPayment() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // TODO: Add payment processing logic here
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}
