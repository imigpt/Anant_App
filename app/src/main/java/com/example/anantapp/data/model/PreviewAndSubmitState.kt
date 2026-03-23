package com.example.anantapp.data.model

import java.io.Serializable

/**
 * UI State for Preview & Submit Screen
 * Manages the state of fundraiser preview, verification status, and submission
 */
data class PreviewAndSubmitState(
    // Fundraiser Details
    val fundraiserId: String = "",
    val fundraiserTitle: String = "",
    val fundraiserStory: String = "",
    val goalAmount: Long = 0L, // in Paisa
    val goalAmountFormatted: String = "₹ 0",
    
    // Verification Status
    val isBeneficiaryVerified: Boolean = false,
    val isDocumentsVerified: Boolean = false,
    
    // Loading and Error States
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    
    // Form Validation
    val isFormValid: Boolean = false,
    val isDraft: Boolean = false
) : Serializable

/**
 * Result states for Preview & Submit operations
 */
sealed class PreviewAndSubmitResult {
    object Idle : PreviewAndSubmitResult()
    data class Success(val fundraiserId: String, val message: String = "Submitted for admin approval") : PreviewAndSubmitResult()
    data class DraftSaved(val fundraiserId: String) : PreviewAndSubmitResult()
    data class Error(val message: String) : PreviewAndSubmitResult()
}
