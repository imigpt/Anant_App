package com.example.anantapp.feature.location.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for location sharing state
data class ShareRealTimeLocationUiState(
    val isLocationSharingEnabled: Boolean = false,
    val selectedMembers: List<FamilyMember> = emptyList(),
    val allMembers: List<FamilyMember> = emptyList(),
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

data class FamilyMember(
    val id: String = "",
    val name: String = "",
    val isSelected: Boolean = false
) : Serializable

// Result sealed class for location sharing operations
sealed class ShareLocationResult {
    object Idle : ShareLocationResult()
    object Loading : ShareLocationResult()
    data class Success(val message: String = "Location shared successfully") : ShareLocationResult()
    data class Error(val message: String) : ShareLocationResult()
}

class ShareRealTimeLocationViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(
        ShareRealTimeLocationUiState(
            allMembers = listOf(
                FamilyMember(id = "1", name = "Wife", isSelected = false),
                FamilyMember(id = "2", name = "Son", isSelected = false),
                FamilyMember(id = "3", name = "Mother", isSelected = false),
                FamilyMember(id = "4", name = "Brother", isSelected = false)
            )
        )
    )
    val state: StateFlow<ShareRealTimeLocationUiState> = _state.asStateFlow()
    
    private val _locationResult = MutableStateFlow<ShareLocationResult>(ShareLocationResult.Idle)
    val locationResult: StateFlow<ShareLocationResult> = _locationResult.asStateFlow()
    
    fun toggleLocationSharing(enabled: Boolean) {
        _state.value = _state.value.copy(
            isLocationSharingEnabled = enabled,
            selectedMembers = if (!enabled) emptyList() else _state.value.selectedMembers
        )
    }
    
    fun toggleMemberSelection(memberId: String) {
        val updatedMembers = _state.value.allMembers.map {
            if (it.id == memberId) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        
        _state.value = _state.value.copy(
            allMembers = updatedMembers,
            selectedMembers = updatedMembers.filter { it.isSelected }
        )
    }
    
    fun shareLocation() {
        if (!_state.value.isLocationSharingEnabled) {
            _locationResult.value = ShareLocationResult.Error("Please enable location sharing first")
            return
        }
        
        if (_state.value.selectedMembers.isEmpty()) {
            _locationResult.value = ShareLocationResult.Error("Please select at least one family member")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Sharing location...")
        
        // Simulate network call
        _locationResult.value = ShareLocationResult.Success()
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Location shared with ${_state.value.selectedMembers.size} member(s)"
        )
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _locationResult.value = ShareLocationResult.Idle
    }
}
