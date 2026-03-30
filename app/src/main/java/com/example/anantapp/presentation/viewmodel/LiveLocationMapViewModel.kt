package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FamilyMemberLocation(
    val id: String,
    val name: String,
    val currentAddress: String,
    val lastSeen: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class LiveLocationMapState(
    val selectedMember: FamilyMemberLocation = FamilyMemberLocation(
        id = "1",
        name = "Kamla",
        currentAddress = "Horizon Towers Jaipur",
        lastSeen = "2 hrs ago",
        latitude = 26.9124,
        longitude = 75.7873
    ),
    val allMembers: List<FamilyMemberLocation> = listOf(
        FamilyMemberLocation(
            id = "1",
            name = "Kamla",
            currentAddress = "Horizon Towers Jaipur",
            lastSeen = "2 hrs ago"
        ),
        FamilyMemberLocation(
            id = "2",
            name = "Raju",
            currentAddress = "Central Mall Jaipur",
            lastSeen = "30 mins ago"
        ),
        FamilyMemberLocation(
            id = "3",
            name = "Vimla",
            currentAddress = "City Hospital Jaipur",
            lastSeen = "5 mins ago"
        )
    ),
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class LiveLocationMapViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(LiveLocationMapState())
    val state: StateFlow<LiveLocationMapState> = _state.asStateFlow()
    
    fun navigateToLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                _state.value = _state.value.copy(
                    successMessage = "Opening navigation...",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun showMyLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                _state.value = _state.value.copy(
                    successMessage = "Showing your location...",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun showAllMembers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                _state.value = _state.value.copy(
                    successMessage = "Showing all family members...",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun selectMember(memberId: String) {
        val member = _state.value.allMembers.find { it.id == memberId }
        if (member != null) {
            _state.value = _state.value.copy(selectedMember = member)
        }
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
