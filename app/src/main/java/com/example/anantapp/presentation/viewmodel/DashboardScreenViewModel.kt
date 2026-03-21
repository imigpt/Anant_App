package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Production-level ViewModel for DashboardScreen
 */
data class DashboardScreenState(
    val selectedNavItem: String = "analytics",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class DashboardScreenViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardScreenState())
    val uiState: StateFlow<DashboardScreenState> = _uiState.asStateFlow()

    fun onNavigationItemClick(item: String) {
        _uiState.value = _uiState.value.copy(selectedNavItem = item)
    }
}
