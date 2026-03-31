package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddBalanceUiState(
    val amount: String = "500",
    val selectedAmount: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class AddBalanceResult {
    object Idle : AddBalanceResult()
    data class Success(val transactionId: String) : AddBalanceResult()
    data class Error(val message: String) : AddBalanceResult()
}

class AddBalanceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddBalanceUiState())
    val uiState: StateFlow<AddBalanceUiState> = _uiState.asStateFlow()

    fun updateAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    fun selectQuickAmount(amount: String) {
        _uiState.value = _uiState.value.copy(selectedAmount = amount, amount = amount)
    }

    fun proceedWithPayment() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
    }
}
