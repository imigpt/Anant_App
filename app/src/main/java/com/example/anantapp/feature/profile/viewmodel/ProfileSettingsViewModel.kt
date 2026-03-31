package com.example.anantapp.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for profile settings state
data class ProfileSettingsUiState(
    val userName: String = "Mahendra",
    val anantId: String = "#9121038605",
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

// Result sealed class for settings operations
sealed class ProfileSettingsResult {
    object Idle : ProfileSettingsResult()
    object Loading : ProfileSettingsResult()
    data class Success(val message: String = "Settings updated") : ProfileSettingsResult()
    data class Error(val message: String) : ProfileSettingsResult()
    object LogoutSuccess : ProfileSettingsResult()
}

class ProfileSettingsViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(ProfileSettingsUiState())
    val state: StateFlow<ProfileSettingsUiState> = _state.asStateFlow()
    
    private val _settingsResult = MutableStateFlow<ProfileSettingsResult>(ProfileSettingsResult.Idle)
    val settingsResult: StateFlow<ProfileSettingsResult> = _settingsResult.asStateFlow()
    
    fun updateContactInfo() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating contact information...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Contact information updated successfully"
        )
        
        _settingsResult.value = ProfileSettingsResult.Success("Contact information updated")
    }
    
    fun updateFamilyInfo() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating family information...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Family information updated successfully"
        )
        
        _settingsResult.value = ProfileSettingsResult.Success("Family information updated")
    }
    
    fun updateBankAccounts() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating bank accounts...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Bank accounts updated successfully"
        )
        
        _settingsResult.value = ProfileSettingsResult.Success("Bank accounts updated")
    }
    
    fun updateInsurancePolicies() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating insurance policies...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Insurance policies updated successfully"
        )
        
        _settingsResult.value = ProfileSettingsResult.Success("Insurance policies updated")
    }
    
    fun updateMedicalConditions() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating medical conditions...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Medical conditions updated successfully"
        )
        
        _settingsResult.value = ProfileSettingsResult.Success("Medical conditions updated")
    }
    
    fun logout() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Logging out...")
        
        // Simulate logout
        _settingsResult.value = ProfileSettingsResult.LogoutSuccess
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _settingsResult.value = ProfileSettingsResult.Idle
    }
}
