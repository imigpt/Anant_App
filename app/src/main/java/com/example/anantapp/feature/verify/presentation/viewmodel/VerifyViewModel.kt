package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VerifyUiState(
    val uploadedDocuments: List<DocumentUpload> = emptyList(),
    val selectedDocuments: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

data class DocumentUpload(
    val id: String,
    val documentType: String,
    val filePath: String,
    val uploadedAt: String,
    val status: String // "pending", "approved", "rejected"
)

sealed class VerifyResult {
    object Idle : VerifyResult()
    data class Success(val message: String) : VerifyResult()
    data class Error(val message: String) : VerifyResult()
}

class VerifyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyUiState())
    val uiState: StateFlow<VerifyUiState> = _uiState.asStateFlow()

    fun uploadDocument(documentType: String, filePath: String) {
        val newDocument = DocumentUpload(
            id = "doc_${System.currentTimeMillis()}",
            documentType = documentType,
            filePath = filePath,
            uploadedAt = "Now",
            status = "pending"
        )
        val updatedDocuments = _uiState.value.uploadedDocuments + newDocument
        _uiState.value = _uiState.value.copy(uploadedDocuments = updatedDocuments)
    }

    fun selectDocument(documentType: String) {
        val currentSelected = _uiState.value.selectedDocuments.toMutableList()
        if (currentSelected.contains(documentType)) {
            currentSelected.remove(documentType)
        } else {
            currentSelected.add(documentType)
        }
        _uiState.value = _uiState.value.copy(selectedDocuments = currentSelected)
    }

    fun removeDocument(documentId: String) {
        val updatedDocuments = _uiState.value.uploadedDocuments.filter { it.id != documentId }
        _uiState.value = _uiState.value.copy(uploadedDocuments = updatedDocuments)
    }

    fun submitVerification() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
