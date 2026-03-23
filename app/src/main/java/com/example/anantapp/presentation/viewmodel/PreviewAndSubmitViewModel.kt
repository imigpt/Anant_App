package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.anantapp.data.model.PreviewAndSubmitState
import com.example.anantapp.data.model.PreviewAndSubmitResult

/**
 * ViewModel for Preview & Submit Screen
 * Manages fundraiser submission workflow and verification states
 */
class PreviewAndSubmitViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(PreviewAndSubmitState())
    val uiState: StateFlow<PreviewAndSubmitState> = _uiState.asStateFlow()
    
    private val _result = MutableStateFlow<PreviewAndSubmitResult>(PreviewAndSubmitResult.Idle)
    val result: StateFlow<PreviewAndSubmitResult> = _result.asStateFlow()
    
    // ==================== Fundraiser Data ===================
    
    /**
     * Initialize with fundraiser data from previous steps
     */
    fun initializeFundraiserData(
        fundraiserId: String,
        title: String,
        story: String,
        goalAmountInPaisa: Long
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                fundraiserId = fundraiserId,
                fundraiserTitle = title,
                fundraiserStory = story.take(50), // Truncate for preview
                goalAmount = goalAmountInPaisa,
                goalAmountFormatted = formatGoalAmount(goalAmountInPaisa),
                isFormValid = true // Assuming data from previous steps is valid
            )
        }
    }
    
    /**
     * Set verification status (from backend response)
     */
    fun setVerificationStatus(
        beneficiaryVerified: Boolean,
        documentsVerified: Boolean
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isBeneficiaryVerified = beneficiaryVerified,
                isDocumentsVerified = documentsVerified
            )
        }
    }
    
    // ==================== Submission Actions ===================
    
    /**
     * Submit fundraiser for admin approval
     */
    fun submitFundraiser() {
        val currentState = _uiState.value
        
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Simulate network delay for submission
                kotlinx.coroutines.delay(1500)
                
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        isDraft = false,
                        errorMessage = null
                    )
                }
                _result.update { 
                    PreviewAndSubmitResult.Success(
                        fundraiserId = currentState.fundraiserId,
                        message = "Fundraiser submitted for admin approval successfully!"
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _result.update { 
                    PreviewAndSubmitResult.Error("Failed to submit: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Save as draft instead of submitting
     */
    fun saveDraft() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Simulate network delay for draft save
                kotlinx.coroutines.delay(800)
                
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        isDraft = true,
                        errorMessage = null
                    )
                }
                _result.update { 
                    PreviewAndSubmitResult.DraftSaved(fundraiserId = _uiState.value.fundraiserId)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _result.update { 
                    PreviewAndSubmitResult.Error("Failed to save draft: ${e.message}")
                }
            }
        }
    }
    
    // ==================== Helper Functions ===================
    
    /**
     * Format goal amount from Paisa to INR with proper formatting
     */
    private fun formatGoalAmount(amountInPaisa: Long): String {
        val amountInRupees = amountInPaisa / 100.0
        return when {
            amountInRupees >= 100000 -> {
                val lakhs = amountInRupees / 100000
                if (lakhs == lakhs.toLong().toDouble()) {
                    "₹ ${lakhs.toLong().toInt() / 100},${(lakhs.toLong().toInt() % 100).toString().padStart(2, '0')}"
                } else {
                    "₹ ${"%.2f".format(amountInRupees)}"
                }
            }
            amountInRupees >= 1000 -> {
                val thousands = amountInRupees / 1000
                "₹ ${"%.1f".format(thousands)}K"
            }
            else -> "₹ ${"%.0f".format(amountInRupees)}"
        }
    }
    
    /**
     * Reset result state
     */
    fun resetResult() {
        _result.update { PreviewAndSubmitResult.Idle }
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
