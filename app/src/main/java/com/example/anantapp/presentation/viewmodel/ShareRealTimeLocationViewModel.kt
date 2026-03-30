package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FamilyMember(
    val id: String,
    val name: String,
    val relation: String
)

data class ShareRealTimeLocationState(
    val isLocationSharingEnabled: Boolean = false,
    val selectedMembers: Set<String> = emptySet(),
    val familyMembers: List<FamilyMember> = listOf(
        FamilyMember("1", "My Son", "Son"),
        FamilyMember("2", "My Daughter", "Daughter"),
        FamilyMember("3", "My Wife", "Spouse"),
        FamilyMember("4", "My Friend", "Friend")
    ),
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class ShareRealTimeLocationViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(ShareRealTimeLocationState())
    val state: StateFlow<ShareRealTimeLocationState> = _state.asStateFlow()
    
    fun toggleLocationSharing(enabled: Boolean) {
        _state.value = _state.value.copy(
            isLocationSharingEnabled = enabled
        )
    }
    
    fun toggleMemberSelection(memberId: String) {
        val currentSelection = _state.value.selectedMembers.toMutableSet()
        if (currentSelection.contains(memberId)) {
            currentSelection.remove(memberId)
        } else {
            currentSelection.add(memberId)
        }
        _state.value = _state.value.copy(
            selectedMembers = currentSelection
        )
    }
    
    fun shareLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                // Simulate API call
                kotlinx.coroutines.delay(1500)
                
                if (_state.value.selectedMembers.isEmpty()) {
                    _state.value = _state.value.copy(
                        errorMessage = "Please select at least one family member",
                        isLoading = false
                    )
                } else if (!_state.value.isLocationSharingEnabled) {
                    _state.value = _state.value.copy(
                        errorMessage = "Please enable location sharing first",
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        successMessage = "Location shared with ${_state.value.selectedMembers.size} member(s)",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun manageFamilyMembers() {
        // Navigate to manage family members screen
        viewModelScope.launch {
            // This will be handled by navigation
        }
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
