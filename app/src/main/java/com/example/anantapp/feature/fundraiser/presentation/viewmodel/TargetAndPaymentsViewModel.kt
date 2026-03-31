package com.example.anantapp.feature.fundraiser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TargetAndPaymentsState(
    val targetAmount: String = "",
    val currency: String = "INR",
    val paymentMethods: List<String> = emptyList(),
    val isAutoTopupEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TargetAndPaymentsResult {
    object Idle : TargetAndPaymentsResult()
    object DraftSaved : TargetAndPaymentsResult()
    data class Success(val fundraiserId: String) : TargetAndPaymentsResult()
    data class Error(val message: String) : TargetAndPaymentsResult()
}

class TargetAndPaymentsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TargetAndPaymentsState())
    val uiState: StateFlow<TargetAndPaymentsState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<TargetAndPaymentsResult>(TargetAndPaymentsResult.Idle)
    val result: StateFlow<TargetAndPaymentsResult> = _result.asStateFlow()

    fun updateTargetAmount(amount: String) {
        _uiState.value = _uiState.value.copy(targetAmount = amount)
    }

    fun updateCurrency(currency: String) {
        _uiState.value = _uiState.value.copy(currency = currency)
    }

    fun toggleAutoTopup() {
        _uiState.value = _uiState.value.copy(
            isAutoTopupEnabled = !_uiState.value.isAutoTopupEnabled
        )
    }

    fun saveDraft() {
        _result.value = TargetAndPaymentsResult.DraftSaved
    }

    fun submitFundraiser() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Simulate API call
        _result.value = TargetAndPaymentsResult.Success("fundraiser_123")
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}
