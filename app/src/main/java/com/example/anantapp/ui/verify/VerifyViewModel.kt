package com.example.anantapp.ui.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.VerifyResult
import com.example.anantapp.data.model.VerifyState
import com.example.anantapp.data.repository.VerifyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val verifyRepository: VerifyRepository = VerifyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyState())
    val uiState: StateFlow<VerifyState> = _uiState.asStateFlow()

    /**
     * Marks a specific document as uploaded
     */
    fun markDocumentUploaded(documentType: String) {
        _uiState.update { currentState ->
            val updatedDocuments = currentState.uploadedDocuments + documentType
            currentState.copy(
                uploadedDocuments = updatedDocuments,
                errorMessage = null
            )
        }
    }

    /**
     * Submit verification for all documents
     */
    fun submitVerification() {
        if (!_uiState.value.isSubmitEnabled) {
            _uiState.update { it.copy(errorMessage = "Please upload all documents before submitting") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulating API call for final submission
            val result = verifyRepository.submitVerification("All Documents")
            handleVerifyResult(result)
        }
    }

    private fun handleVerifyResult(result: VerifyResult) {
        when (result) {
            is VerifyResult.Success -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        successMessage = result.message
                    )
                }
            }
            is VerifyResult.Error -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
            VerifyResult.Loading -> {
                _uiState.update { it.copy(isLoading = true) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }
}