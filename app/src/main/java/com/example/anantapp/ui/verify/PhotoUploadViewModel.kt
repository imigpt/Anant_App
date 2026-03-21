package com.example.anantapp.ui.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.PhotoUploadResult
import com.example.anantapp.data.model.PhotoUploadState
import com.example.anantapp.data.repository.PhotoUploadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoUploadViewModel : ViewModel() {
    private val repository = PhotoUploadRepository()
    
    private val _uiState = MutableStateFlow(PhotoUploadState())
    val uiState: StateFlow<PhotoUploadState> = _uiState.asStateFlow()

    fun selectPhoto(photoUri: String) {
        _uiState.value = _uiState.value.copy(
            photoUri = photoUri,
            isPhotoSelected = true
        )
    }

    fun takePhoto() {
        // This would typically launch camera intent
        // For now, simulate with a dummy URI
        selectPhoto("content://media/external/images/media/photo_${System.currentTimeMillis()}")
    }

    fun choosePhoto() {
        // This would typically launch file picker
        // For now, simulate with a dummy URI
        selectPhoto("content://media/external/images/media/photo_${System.currentTimeMillis()}")
    }

    fun submitPhoto() {
        val currentState = _uiState.value
        
        if (!currentState.isPhotoSelected) {
            _uiState.value = currentState.copy(
                errorMessage = "Please select a photo"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            
            try {
                val result = repository.uploadPhoto(currentState.photoUri ?: "")
                handlePhotoUploadResult(result)
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }

    private fun handlePhotoUploadResult(result: PhotoUploadResult) {
        val currentState = _uiState.value
        when (result) {
            is PhotoUploadResult.Loading -> {
                _uiState.value = currentState.copy(isLoading = true)
            }
            is PhotoUploadResult.Success -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    successMessage = result.message
                )
            }
            is PhotoUploadResult.Error -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = result.error
                )
            }
        }
    }

    fun clearMessages() {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
