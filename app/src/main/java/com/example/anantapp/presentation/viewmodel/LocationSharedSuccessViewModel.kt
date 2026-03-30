package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationSharedMember(
    val id: String,
    val name: String
)

data class LocationSharedSuccessState(
    val sharedWith: List<LocationSharedMember> = listOf(
        LocationSharedMember("1", "My Son"),
        LocationSharedMember("2", "My Wife"),
        LocationSharedMember("3", "My Wife"),
        LocationSharedMember("4", "My Mother")
    ),
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class LocationSharedSuccessViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(LocationSharedSuccessState())
    val state: StateFlow<LocationSharedSuccessState> = _state.asStateFlow()
    
    fun manageAccess() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(1000)
                
                _state.value = _state.value.copy(
                    successMessage = "Access management opened",
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
    
    fun viewOnMap() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(1000)
                
                _state.value = _state.value.copy(
                    successMessage = "Opening map view...",
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
    
    fun finishSharing() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                // This will be handled by navigation in parent
                _state.value = _state.value.copy(
                    successMessage = "Completed",
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
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
