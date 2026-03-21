package com.example.anantapp.data.model

sealed class PhotoUploadResult {
    object Loading : PhotoUploadResult()
    data class Success(val message: String) : PhotoUploadResult()
    data class Error(val error: String) : PhotoUploadResult()
}

data class PhotoUploadState(
    val photoUri: String? = null,
    val isPhotoSelected: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val title: String = "Upload Your Photo"
) {
    val isSubmitEnabled: Boolean
        get() = isPhotoSelected && !isLoading
}
