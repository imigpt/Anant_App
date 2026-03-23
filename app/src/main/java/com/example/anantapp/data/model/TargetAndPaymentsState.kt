package com.example.anantapp.data.model

/**
 * UI State for Target & Payments Screen (Fundraiser Goal & Payment Setup)
 * Used for setting fundraiser goal amount, deadline, and payment configuration
 */
data class TargetAndPaymentsState(
    // Goal Section
    val goalAmount: String = "",
    val goalAmountInPaisa: Long = 0L,
    
    // Deadline Section (Optional)
    val deadline: String = "",
    val selectedDeadlineDate: Long? = null,
    
    // Bank Account Details
    val accountHolderName: String = "",
    val accountNumber: String = "",
    val ifscCode: String = "",
    
    // UPI Details
    val upiId: String = "",
    
    // Auto Topup (Toggle)
    val isAutoTopupEnabled: Boolean = true,
    
    // Bank UPI/Wallet Link
    val bankUpiId: String = "",
    val walletLink: String = "",
    
    // UI State
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isDraft: Boolean = false,
    
    // Validation state
    val isGoalAmountValid: Boolean = false,
    val isAccountHolderNameValid: Boolean = false,
    val isAccountNumberValid: Boolean = false,
    val isIfscCodeValid: Boolean = false,
    val isUpiIdValid: Boolean = false
) {
    val isFormValid: Boolean
        get() = isGoalAmountValid && 
                isAccountHolderNameValid && 
                isAccountNumberValid && 
                isIfscCodeValid
    
    val minGoalAmountINR: Long
        get() = 25000L // Minimum donation required is INR 25,000 (as per app requirements)
    
    val isGoalAmountSufficient: Boolean
        get() = goalAmountInPaisa >= minGoalAmountINR
}

/**
 * Sealed class for Target & Payments submission results
 */
sealed class TargetAndPaymentsResult {
    object Loading : TargetAndPaymentsResult()
    data class Success(val fundraiserId: String, val message: String = "Fundraiser published successfully") : TargetAndPaymentsResult()
    data class DraftSaved(val message: String = "Draft saved successfully") : TargetAndPaymentsResult()
    data class Error(val message: String) : TargetAndPaymentsResult()
    object Idle : TargetAndPaymentsResult()
}
