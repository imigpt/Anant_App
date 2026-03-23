package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.anantapp.data.model.TargetAndPaymentsState
import com.example.anantapp.data.model.TargetAndPaymentsResult

/**
 * ViewModel for Target & Payments Screen
 * Manages fundraiser goal amount, deadline, and payment configuration
 */
class TargetAndPaymentsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(TargetAndPaymentsState())
    val uiState: StateFlow<TargetAndPaymentsState> = _uiState.asStateFlow()
    
    private val _result = MutableStateFlow<TargetAndPaymentsResult>(TargetAndPaymentsResult.Idle)
    val result: StateFlow<TargetAndPaymentsResult> = _result.asStateFlow()
    
    // ==================== Goal Amount ====================
    
    /**
     * Update goal amount in INR format
     * Converts to Paisa (multiply by 100)
     */
    fun updateGoalAmount(amountStr: String) {
        // Remove any non-digit characters
        val cleanedAmount = amountStr.filter { it.isDigit() }
        
        val amountInPaisa = if (cleanedAmount.isNotEmpty()) {
            cleanedAmount.toLong() * 100
        } else {
            0L
        }
        
        _uiState.update { currentState ->
            val isValid = amountInPaisa > 0 && amountInPaisa >= (currentState.minGoalAmountINR * 100)
            currentState.copy(
                goalAmount = cleanedAmount,
                goalAmountInPaisa = amountInPaisa,
                isGoalAmountValid = isValid,
                errorMessage = if (amountInPaisa > 0 && amountInPaisa < (currentState.minGoalAmountINR * 100)) {
                    "Minimum goal amount is ₹${currentState.minGoalAmountINR / 1000}K"
                } else null
            )
        }
    }
    
    // ==================== Deadline ====================
    
    /**
     * Update fundraiser deadline
     */
    fun updateDeadline(dateInMillis: Long?, dateString: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeadlineDate = dateInMillis,
                deadline = dateString,
                errorMessage = null
            )
        }
    }
    
    /**
     * Clear deadline
     */
    fun clearDeadline() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeadlineDate = null,
                deadline = "",
                errorMessage = null
            )
        }
    }
    
    // ==================== Bank Account Details ====================
    
    /**
     * Update account holder name
     */
    fun updateAccountHolderName(name: String) {
        _uiState.update { currentState ->
            val isValid = name.trim().length >= 3
            currentState.copy(
                accountHolderName = name,
                isAccountHolderNameValid = isValid
            )
        }
    }
    
    /**
     * Update account number
     */
    fun updateAccountNumber(accountNumber: String) {
        // Remove any non-digit characters
        val cleanedNumber = accountNumber.filter { it.isDigit() }
        
        _uiState.update { currentState ->
            // Account number should be between 9-18 digits
            val isValid = cleanedNumber.length in 9..18
            currentState.copy(
                accountNumber = cleanedNumber,
                isAccountNumberValid = isValid
            )
        }
    }
    
    /**
     * Update IFSC code
     */
    fun updateIfscCode(ifscCode: String) {
        val cleanedCode = ifscCode.uppercase()
        
        _uiState.update { currentState ->
            // IFSC code should be 11 characters (format: XXXX0XXXXXX)
            val isValid = cleanedCode.length == 11
            currentState.copy(
                ifscCode = cleanedCode,
                isIfscCodeValid = isValid
            )
        }
    }
    
    // ==================== UPI Details ====================
    
    /**
     * Update UPI ID
     */
    fun updateUpiId(upiId: String) {
        val cleanedUpiId = upiId.trim().lowercase()
        
        _uiState.update { currentState ->
            // Simple UPI validation: should contain @ symbol
            val isValid = cleanedUpiId.contains("@") && cleanedUpiId.isNotEmpty()
            currentState.copy(
                upiId = cleanedUpiId,
                isUpiIdValid = isValid
            )
        }
    }
    
    // ==================== Auto Topup & Wallet ====================
    
    /**
     * Toggle auto topup feature
     */
    fun toggleAutoTopup() {
        _uiState.update { currentState ->
            currentState.copy(isAutoTopupEnabled = !currentState.isAutoTopupEnabled)
        }
    }
    
    /**
     * Update bank UPI ID for wallet integration
     */
    fun updateBankUpiId(upiId: String) {
        _uiState.update { currentState ->
            currentState.copy(bankUpiId = upiId.trim())
        }
    }
    
    /**
     * Update wallet link
     */
    fun updateWalletLink(link: String) {
        _uiState.update { currentState ->
            currentState.copy(walletLink = link.trim())
        }
    }
    
    // ==================== Form Submission ====================
    
    /**
     * Save as draft
     */
    fun saveDraft() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Simulate network delay
                kotlinx.coroutines.delay(500)
                
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isDraft = true,
                        errorMessage = null
                    )
                }
                _result.update { TargetAndPaymentsResult.DraftSaved() }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _result.update { TargetAndPaymentsResult.Error("Failed to save draft: ${e.message}") }
            }
        }
    }
    
    /**
     * Publish/Submit fundraiser
     */
    fun publishFundraiser() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Simulate network delay
                kotlinx.coroutines.delay(1000)
                
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isDraft = false,
                        errorMessage = null
                    )
                }
                _result.update { TargetAndPaymentsResult.Success(fundraiserId = "fr_${System.currentTimeMillis()}") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _result.update { TargetAndPaymentsResult.Error("Failed to publish fundraiser: ${e.message}") }
            }
        }
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
    
    /**
     * Reset result state
     */
    fun resetResult() {
        _result.update { TargetAndPaymentsResult.Idle }
    }
}
