package com.example.anantapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.repository.ThemeRepository
import com.example.anantapp.ui.theme.ScreenTheme
import com.example.anantapp.ui.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ThemeState(
    val currentScreenTheme: ScreenTheme = ScreenTheme.PROFILE,
    val theme: com.example.anantapp.ui.theme.CurrentTheme = ThemeManager.getTheme(ScreenTheme.PROFILE),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Theme ViewModel - MVVM state management for themes
 * Manages theme selection and provides theme data to UI layers
 */
class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    
    private val themeRepository = ThemeRepository(application)
    
    private val _themeState = MutableStateFlow(ThemeState())
    val themeState: StateFlow<ThemeState> = _themeState.asStateFlow()
    
    init {
        observeThemeChanges()
    }
    
    private fun observeThemeChanges() {
        viewModelScope.launch {
            themeRepository.currentTheme.collect { theme ->
                updateTheme(theme)
            }
        }
    }
    
    fun changeTheme(screenTheme: ScreenTheme) {
        viewModelScope.launch {
            try {
                _themeState.value = _themeState.value.copy(isLoading = true)
                themeRepository.setCurrentTheme(screenTheme)
                updateTheme(screenTheme)
                _themeState.value = _themeState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _themeState.value = _themeState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to change theme"
                )
            }
        }
    }
    
    fun setVerifyScreenTheme() {
        changeTheme(ScreenTheme.VERIFY)
    }
    
    fun setWalletScreenTheme() {
        changeTheme(ScreenTheme.WALLET)
    }
    
    fun setNomineeScreenTheme() {
        changeTheme(ScreenTheme.NOMINEE)
    }
    
    fun setProfileScreenTheme() {
        changeTheme(ScreenTheme.PROFILE)
    }
    
    private fun updateTheme(screenTheme: ScreenTheme) {
        val newTheme = ThemeManager.getTheme(screenTheme)
        _themeState.value = _themeState.value.copy(
            currentScreenTheme = screenTheme,
            theme = newTheme
        )
    }
    
    fun clearError() {
        _themeState.value = _themeState.value.copy(errorMessage = null)
    }
}
