package com.example.anantapp.feature.location.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for live location map state
data class LiveLocationMapUiState(
    val currentUserLocation: LocationData = LocationData(),
    val familyMemberLocation: FamilyMemberLocationData = FamilyMemberLocationData(),
    val allMembersLocations: List<FamilyMemberLocationData> = emptyList(),
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
) : Serializable

data class FamilyMemberLocationData(
    val id: String = "1",
    val name: String = "My Son",
    val relationship: String = "Son",
    val phoneNumber: String = "+91-9876543210",
    val location: LocationData = LocationData(latitude = 28.6139, longitude = 77.2090, address = "New Delhi, India"),
    val lastSeenTime: String = "Just now"
) : Serializable

// Result sealed class for map operations
sealed class LiveLocationMapResult {
    object Idle : LiveLocationMapResult()
    object Loading : LiveLocationMapResult()
    data class Success(val message: String = "Location data loaded") : LiveLocationMapResult()
    data class Error(val message: String) : LiveLocationMapResult()
}

class LiveLocationMapViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(
        LiveLocationMapUiState(
            currentUserLocation = LocationData(latitude = 28.6139, longitude = 77.2090, address = "New Delhi, India"),
            familyMemberLocation = FamilyMemberLocationData(
                id = "1",
                name = "My Son",
                relationship = "Son",
                phoneNumber = "+91-9876543210",
                location = LocationData(latitude = 28.6139, longitude = 77.2090, address = "New Delhi, India"),
                lastSeenTime = "Just now"
            ),
            allMembersLocations = listOf(
                FamilyMemberLocationData(
                    id = "1",
                    name = "My Son",
                    relationship = "Son",
                    location = LocationData(latitude = 28.6139, longitude = 77.2090, address = "New Delhi"),
                    lastSeenTime = "Just now"
                ),
                FamilyMemberLocationData(
                    id = "2",
                    name = "My Wife",
                    relationship = "Spouse",
                    location = LocationData(latitude = 28.5355, longitude = 77.3910, address = "Gurgaon"),
                    lastSeenTime = "2 minutes ago"
                ),
                FamilyMemberLocationData(
                    id = "3",
                    name = "My Mother",
                    relationship = "Parent",
                    location = LocationData(latitude = 28.7041, longitude = 77.1025, address = "South Delhi"),
                    lastSeenTime = "5 minutes ago"
                )
            )
        )
    )
    val state: StateFlow<LiveLocationMapUiState> = _state.asStateFlow()
    
    private val _mapResult = MutableStateFlow<LiveLocationMapResult>(LiveLocationMapResult.Idle)
    val mapResult: StateFlow<LiveLocationMapResult> = _mapResult.asStateFlow()
    
    fun showMyLocation() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Loading your location...")
        
        // Simulate fetching user's location
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Your location: New Delhi, India"
        )
        
        _mapResult.value = LiveLocationMapResult.Success("Your location loaded")
    }
    
    fun showAllMembers() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Loading all members...")
        
        // Simulate fetching all members' locations
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "All family members' locations loaded"
        )
        
        _mapResult.value = LiveLocationMapResult.Success("All members' locations loaded")
    }
    
    fun navigateToMember(memberId: String) {
        val member = _state.value.allMembersLocations.find { it.id == memberId }
        if (member != null) {
            _state.value = _state.value.copy(
                isLoading = true,
                loadingMessage = "Navigating to ${member.name}..."
            )
            
            // Simulate navigation
            _state.value = _state.value.copy(
                isLoading = false,
                successMessage = "Navigation to ${member.name} started"
            )
            
            _mapResult.value = LiveLocationMapResult.Success("Navigation started")
        } else {
            _mapResult.value = LiveLocationMapResult.Error("Location not found")
        }
    }
    
    fun updateMemberLocation(memberId: String, latitude: Double, longitude: Double, address: String) {
        val updatedMembers = _state.value.allMembersLocations.map {
            if (it.id == memberId) {
                it.copy(
                    location = LocationData(latitude = latitude, longitude = longitude, address = address),
                    lastSeenTime = "Just now"
                )
            } else {
                it
            }
        }
        
        _state.value = _state.value.copy(allMembersLocations = updatedMembers)
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _mapResult.value = LiveLocationMapResult.Idle
    }
}
