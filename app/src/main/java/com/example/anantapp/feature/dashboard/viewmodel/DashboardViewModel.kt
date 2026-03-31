package com.example.anantapp.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class DashboardResult {
    data object Idle : DashboardResult()
    data object NavigateHome : DashboardResult()
    data object NavigateAnalytics : DashboardResult()
    data object NavigateNotifications : DashboardResult()
    data object NavigateProfile : DashboardResult()
    data class Error(val message: String) : DashboardResult()
}

data class DashboardState(
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = false,
    val messageText: String = ""
)

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _result = MutableStateFlow<DashboardResult>(DashboardResult.Idle)
    val result: StateFlow<DashboardResult> = _result.asStateFlow()

    fun onHomeClick() {
        _state.value = _state.value.copy(selectedTabIndex = 0)
        _result.value = DashboardResult.NavigateHome
    }

    fun onAnalyticsClick() {
        _state.value = _state.value.copy(selectedTabIndex = 1)
        _result.value = DashboardResult.NavigateAnalytics
    }

    fun onNotificationClick() {
        _state.value = _state.value.copy(selectedTabIndex = 2)
        _result.value = DashboardResult.NavigateNotifications
    }

    fun onProfileClick() {
        _state.value = _state.value.copy(selectedTabIndex = 3)
        _result.value = DashboardResult.NavigateProfile
    }

    fun selectTab(tabIndex: Int) {
        _state.value = _state.value.copy(selectedTabIndex = tabIndex)
    }

    fun resetResult() {
        _result.value = DashboardResult.Idle
    }

    fun updateMessage(message: String) {
        _state.value = _state.value.copy(messageText = message)
    }

    fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}
