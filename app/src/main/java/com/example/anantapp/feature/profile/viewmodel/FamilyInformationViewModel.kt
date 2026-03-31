package com.example.anantapp.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for family information state
data class FamilyInformationUiState(
    val familyHeadName: String = "Mahendra",
    val spouseName: String = "Tusharika",
    val spouseAge: String = "23",
    val nominee1: String = "Yashu",
    val nominee2: String = "Raju",
    val maritalStatus: String = "Married",
    val familyInsuranceStatus: String = "Active",
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

// Result sealed class for family operations
sealed class FamilyInformationResult {
    object Idle : FamilyInformationResult()
    object Loading : FamilyInformationResult()
    data class Success(val message: String = "Family information updated") : FamilyInformationResult()
    data class Error(val message: String) : FamilyInformationResult()
}

class FamilyInformationViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(FamilyInformationUiState())
    val state: StateFlow<FamilyInformationUiState> = _state.asStateFlow()
    
    private val _familyResult = MutableStateFlow<FamilyInformationResult>(FamilyInformationResult.Idle)
    val familyResult: StateFlow<FamilyInformationResult> = _familyResult.asStateFlow()
    
    fun updateFamilyHeadName(value: String) {
        if (value.isBlank() || value.length <= 100) {
            _state.value = _state.value.copy(familyHeadName = value)
        }
    }
    
    fun updateSpouseName(value: String) {
        if (value.isBlank() || value.length <= 100) {
            _state.value = _state.value.copy(spouseName = value)
        }
    }
    
    fun updateSpouseAge(value: String) {
        val filtered = value.filter { it.isDigit() }.take(3)
        if (filtered.isNotEmpty()) {
            val age = filtered.toInt()
            if (age <= 120) {
                _state.value = _state.value.copy(spouseAge = filtered)
            }
        } else if (filtered.isEmpty()) {
            _state.value = _state.value.copy(spouseAge = "")
        }
    }
    
    fun updateNominee1(value: String) {
        if (value.isBlank() || value.length <= 100) {
            _state.value = _state.value.copy(nominee1 = value)
        }
    }
    
    fun updateNominee2(value: String) {
        if (value.isBlank() || value.length <= 100) {
            _state.value = _state.value.copy(nominee2 = value)
        }
    }
    
    fun updateMaritalStatus(value: String) {
        _state.value = _state.value.copy(maritalStatus = value)
    }
    
    fun updateFamilyInsuranceStatus(value: String) {
        _state.value = _state.value.copy(familyInsuranceStatus = value)
    }
    
    fun updateFamilyInformation() {
        // Validate required fields
        if (_state.value.familyHeadName.isBlank() || 
            _state.value.maritalStatus.isBlank()) {
            _familyResult.value = FamilyInformationResult.Error("Please fill in required family information")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating family information...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Family information requests submitted for approval"
        )
        
        _familyResult.value = FamilyInformationResult.Success("Family information updated")
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _familyResult.value = FamilyInformationResult.Idle
    }
}
