package com.example.anantapp.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for Onboarding Screen
 * Manages onboarding state and navigation
 */
class OnboardingViewModel : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _onboardingComplete = MutableStateFlow(false)
    val onboardingComplete: StateFlow<Boolean> = _onboardingComplete.asStateFlow()

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun completeOnboarding() {
        _onboardingComplete.value = true
    }

    fun resetOnboarding() {
        _currentPage.value = 0
        _onboardingComplete.value = false
    }
}
