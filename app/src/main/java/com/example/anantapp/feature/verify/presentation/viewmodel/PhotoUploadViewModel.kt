package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PhotoUploadUiState(
    val photoPath: String? = null,
    val isPhotoSelected: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class PhotoUploadResult {
    object Idle : PhotoUploadResult()
    data class Success(val message: String) : PhotoUploadResult()
    data class Error(val message: String) : PhotoUploadResult()
}

class PhotoUploadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PhotoUploadUiState())
    val uiState: StateFlow<PhotoUploadUiState> = _uiState.asStateFlow()

    fun selectPhoto(photoPath: String) {
        _uiState.value = _uiState.value.copy(
            photoPath = photoPath,
            isPhotoSelected = true
        )
    }

    fun clearPhoto() {
        _uiState.value = _uiState.value.copy(
            photoPath = null,
            isPhotoSelected = false
        )
    }

    fun submitPhotoVerification() {
        if (_uiState.value.isPhotoSelected) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // API call would go here
        } else {
            _uiState.value = _uiState.value.copy(errorMessage = "Please select a photo")
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
