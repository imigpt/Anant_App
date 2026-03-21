package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Production-level ViewModel for BalanceScreen
 */
data class BalanceScreenState(
    val selectedNavItem: String = "home",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class BalanceScreenViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(BalanceScreenState())
    val uiState: StateFlow<BalanceScreenState> = _uiState.asStateFlow()

    fun onNavigationItemClick(item: String) {
        _uiState.value = _uiState.value.copy(selectedNavItem = item)
    }
}
