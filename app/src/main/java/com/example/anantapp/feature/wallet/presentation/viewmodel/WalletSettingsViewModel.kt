package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class WalletSettingsUiState(
    val autoDebitEnabled: Boolean = false,
    val topUpThreshold: String = "₹500",
    val withdrawalRulesAccepted: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class WalletSettingsResult {
    object Idle : WalletSettingsResult()
    data class SettingsUpdated(val message: String) : WalletSettingsResult()
    data class Error(val message: String) : WalletSettingsResult()
}

class WalletSettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WalletSettingsUiState())
    val uiState: StateFlow<WalletSettingsUiState> = _uiState.asStateFlow()

    fun toggleAutoDebit() {
        _uiState.value = _uiState.value.copy(
            autoDebitEnabled = !_uiState.value.autoDebitEnabled
        )
    }

    fun updateTopUpThreshold(threshold: String) {
        _uiState.value = _uiState.value.copy(topUpThreshold = threshold)
    }

    fun acceptWithdrawalRules(accepted: Boolean) {
        _uiState.value = _uiState.value.copy(withdrawalRulesAccepted = accepted)
    }

    fun toggleNotifications() {
        _uiState.value = _uiState.value.copy(
            notificationsEnabled = !_uiState.value.notificationsEnabled
        )
    }

    fun saveSettings() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
    }
}
