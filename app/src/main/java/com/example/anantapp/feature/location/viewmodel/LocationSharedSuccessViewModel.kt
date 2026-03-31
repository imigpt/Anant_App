package com.example.anantapp.feature.location.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for location shared success state
data class LocationSharedSuccessUiState(
    val sharedMembers: List<String> = listOf("My Son", "My Wife", "My Mother"),
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

// Result sealed class for success operations
sealed class LocationSuccessResult {
    object Idle : LocationSuccessResult()
    object Loading : LocationSuccessResult()
    data class Success(val message: String = "Location management completed") : LocationSuccessResult()
    data class Error(val message: String) : LocationSuccessResult()
}

class LocationSharedSuccessViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(LocationSharedSuccessUiState())
    val state: StateFlow<LocationSharedSuccessUiState> = _state.asStateFlow()
    
    private val _operationResult = MutableStateFlow<LocationSuccessResult>(LocationSuccessResult.Idle)
    val operationResult: StateFlow<LocationSuccessResult> = _operationResult.asStateFlow()
    
    fun manageAccess() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Loading access settings...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Access settings loaded"
        )
        
        _operationResult.value = LocationSuccessResult.Success("Access settings loaded")
    }
    
    fun viewOnMap() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Loading map...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Map loaded successfully"
        )
        
        _operationResult.value = LocationSuccessResult.Success("Map loaded successfully")
    }
    
    fun done() {
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Location sharing setup complete"
        )
        
        _operationResult.value = LocationSuccessResult.Success("Location sharing setup complete")
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _operationResult.value = LocationSuccessResult.Idle
    }
}
