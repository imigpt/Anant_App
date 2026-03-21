package com.example.anantapp.data.model

data class VerifyState(
    val uploadedDocuments: Set<String> = emptySet(), // Tracks which documents are uploaded
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
) {
    // Submit is only enabled when ALL THREE are uploaded
    val isSubmitEnabled: Boolean
        get() = uploadedDocuments.containsAll(listOf("PAN Card", "Aadhaar", "Driving License"))
}

/**
 * Sealed class for type-safe result handling in Verify operations
 */
sealed class VerifyResult {
    object Loading : VerifyResult()
    data class Success(val message: String) : VerifyResult()
    data class Error(val message: String) : VerifyResult()
}

/**
 * Document types available for verification
 */
enum class DocumentType(val displayName: String) {
    PAN_CARD("PAN Card"),
    AADHAAR("Aadhaar"),
    DRIVING_LICENSE("Driving License"),
    PASSPORT("Passport"),
    VOTER_ID("Voter ID")
}