package com.example.anantapp.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for birthday card state
data class BirthdayCardUiState(
    val personName: String = "Mahendra",
    val age: Int = 24,
    val isDonated: Boolean = false,
    val donatedAmount: Int = 0,
    val hasDonated: Boolean = false,
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

// Result sealed class for birthday operations
sealed class BirthdayCardResult {
    object Idle : BirthdayCardResult()
    object Loading : BirthdayCardResult()
    data class Success(val message: String = "Birthday greeting shared") : BirthdayCardResult()
    data class Error(val message: String) : BirthdayCardResult()
    data class DonationSuccess(val amount: Int, val message: String = "Donation successful") : BirthdayCardResult()
}

class BirthdayCardViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(BirthdayCardUiState())
    val state: StateFlow<BirthdayCardUiState> = _state.asStateFlow()
    
    private val _cardResult = MutableStateFlow<BirthdayCardResult>(BirthdayCardResult.Idle)
    val cardResult: StateFlow<BirthdayCardResult> = _cardResult.asStateFlow()
    
    fun setPerson(name: String, age: Int) {
        _state.value = _state.value.copy(personName = name, age = age)
    }
    
    fun shareGreeting() {
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Sharing birthday greeting...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Birthday greeting shared to your network!"
        )
        
        _cardResult.value = BirthdayCardResult.Success("Birthday greeting shared")
    }
    
    fun donate(amount: Int) {
        if (amount <= 0) {
            _cardResult.value = BirthdayCardResult.Error("Please enter a valid donation amount")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Processing donation...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            hasDonated = true,
            donatedAmount = amount,
            successMessage = "Thank you for donating ₹$amount on your birthday!"
        )
        
        _cardResult.value = BirthdayCardResult.DonationSuccess(amount, "Donation of ₹$amount completed successfully")
    }
    
    fun saveDonationPreference(enabled: Boolean) {
        _state.value = _state.value.copy(isDonated = enabled)
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _cardResult.value = BirthdayCardResult.Idle
    }
}
