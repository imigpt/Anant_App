package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class VerifyUiState(
    val uploadedDocuments: List<DocumentUpload> = emptyList(),
    val selectedDocuments: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
) {
    val isSubmitEnabled: Boolean
        get() {
            val types = uploadedDocuments.map { it.documentType }
            return types.contains("PAN Card") && 
                   types.contains("Aadhaar") && 
                   types.contains("Driving License")
        }

    val uploadedDocumentNames: Set<String>
        get() = uploadedDocuments.map { it.documentType }.toSet()
}

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
        _uiState.update { currentState ->
            val newDocument = DocumentUpload(
                id = "doc_${System.currentTimeMillis()}",
                documentType = documentType,
                filePath = filePath,
                uploadedAt = "Now",
                status = "pending"
            )
            currentState.copy(uploadedDocuments = currentState.uploadedDocuments + newDocument)
        }
    }

    fun markDocumentUploaded(documentName: String) {
        uploadDocument(documentName, "dummy_path")
    }

    fun selectDocument(documentType: String) {
        _uiState.update { currentState ->
            val currentSelected = currentState.selectedDocuments.toMutableList()
            if (currentSelected.contains(documentType)) {
                currentSelected.remove(documentType)
            } else {
                currentSelected.add(documentType)
            }
            currentState.copy(selectedDocuments = currentSelected)
        }
    }

    fun removeDocument(documentId: String) {
        _uiState.update { currentState ->
            val updatedDocuments = currentState.uploadedDocuments.filter { it.id != documentId }
            currentState.copy(uploadedDocuments = updatedDocuments)
        }
    }

    fun submitVerification() {
        if (!_uiState.value.isSubmitEnabled) return
        
        _uiState.update { it.copy(isLoading = true) }
        // Simulate API call
        _uiState.update { 
            it.copy(
                isLoading = false,
                successMessage = "Verification Successful"
            )
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
