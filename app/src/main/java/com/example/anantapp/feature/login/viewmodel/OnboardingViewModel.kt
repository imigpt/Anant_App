package com.example.anantapp.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class OnboardingResult {
    data object Idle : OnboardingResult()
    data object OnboardingComplete : OnboardingResult()
    data object NavigateNext : OnboardingResult()
    data object NavigatePrevious : OnboardingResult()
    data class Error(val message: String) : OnboardingResult()
}

data class OnboardingState(
    val currentPage: Int = 0,
    val totalPages: Int = 3,
    val isLoading: Boolean = false
)

class OnboardingViewModel : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    private val _result = MutableStateFlow<OnboardingResult>(OnboardingResult.Idle)
    val result: StateFlow<OnboardingResult> = _result.asStateFlow()

    fun goToNextPage() {
        val currentPage = _state.value.currentPage
        val totalPages = _state.value.totalPages

        if (currentPage < totalPages - 1) {
            _state.value = _state.value.copy(currentPage = currentPage + 1)
            _result.value = OnboardingResult.NavigateNext
        } else {
            // Last page - complete onboarding
            _result.value = OnboardingResult.OnboardingComplete
        }
    }

    fun goToPreviousPage() {
        val currentPage = _state.value.currentPage
        if (currentPage > 0) {
            _state.value = _state.value.copy(currentPage = currentPage - 1)
            _result.value = OnboardingResult.NavigatePrevious
        }
    }

    fun skipOnboarding() {
        _result.value = OnboardingResult.OnboardingComplete
    }

    fun setCurrentPage(pageIndex: Int) {
        if (pageIndex in 0 until _state.value.totalPages) {
            _state.value = _state.value.copy(currentPage = pageIndex)
        }
    }

    fun resetResult() {
        _result.value = OnboardingResult.Idle
    }

    fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}
