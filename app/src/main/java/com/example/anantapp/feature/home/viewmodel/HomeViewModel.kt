package com.example.anantapp.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenUiState(
    val selectedNavItem: String = "home",
    val walletBalance: Double = 10456.05,
    val userName: String = "Mahendra",
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onNavigationItemClick(item: String) {
        _uiState.value = _uiState.value.copy(selectedNavItem = item)
    }

    fun updateWalletBalance(balance: Double) {
        _uiState.value = _uiState.value.copy(walletBalance = balance)
    }

    fun setError(error: String?) {
        _uiState.value = _uiState.value.copy(error = error)
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}
