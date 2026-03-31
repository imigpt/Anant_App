package com.example.anantapp.feature.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for contact information state
data class ContactInformationUiState(
    val bloodGroup: String = "B+",
    val email: String = "mahendra.designs@imigpt.com",
    val category: String = "Businessman",
    val address: String = "611, 6th floor, Aselea Networks, Horizon Tower, Jaipur",
    val aadharCard: String = "1234-1234-1234",
    val panCard: String = "PNA123ASD23",
    val gender: String = "Male",
    val age: String = "23",
    val phoneNumber: String = "1234567890",
    val alternatePhoneNumber: String = "1234567890",
    val profileImageUri: String = "",
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

// Result sealed class for contact operations
sealed class ContactInformationResult {
    object Idle : ContactInformationResult()
    object Loading : ContactInformationResult()
    data class Success(val message: String = "Contact information updated") : ContactInformationResult()
    data class Error(val message: String) : ContactInformationResult()
}

class ContactInformationViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(ContactInformationUiState())
    val state: StateFlow<ContactInformationUiState> = _state.asStateFlow()
    
    private val _contactResult = MutableStateFlow<ContactInformationResult>(ContactInformationResult.Idle)
    val contactResult: StateFlow<ContactInformationResult> = _contactResult.asStateFlow()
    
    fun updateBloodGroup(value: String) {
        _state.value = _state.value.copy(bloodGroup = value)
    }
    
    fun updateEmail(value: String) {
        if (isValidEmail(value) || value.isEmpty()) {
            _state.value = _state.value.copy(email = value)
        } else {
            _contactResult.value = ContactInformationResult.Error("Invalid email format")
        }
    }
    
    fun updateCategory(value: String) {
        _state.value = _state.value.copy(category = value)
    }
    
    fun updateAddress(value: String) {
        _state.value = _state.value.copy(address = value)
    }
    
    fun updateAadharCard(value: String) {
        val formatted = value.take(15).fold("") { acc, char ->
            when {
                char.isDigit() -> if (acc.length == 4 || acc.length == 9) "$acc-$char" else "$acc$char"
                else -> acc
            }
        }
        _state.value = _state.value.copy(aadharCard = formatted)
    }
    
    fun updatePanCard(value: String) {
        val filtered = value.take(10).uppercase()
        _state.value = _state.value.copy(panCard = filtered)
    }
    
    fun updateGender(value: String) {
        _state.value = _state.value.copy(gender = value)
    }
    
    fun updateAge(value: String) {
        val filtered = value.filter { it.isDigit() }.take(3)
        if (filtered.isNotEmpty()) {
            val age = filtered.toInt()
            if (age <= 120) {
                _state.value = _state.value.copy(age = filtered)
            }
        } else if (filtered.isEmpty()) {
            _state.value = _state.value.copy(age = "")
        }
    }
    
    fun updatePhoneNumber(value: String) {
        val filtered = value.filter { it.isDigit() }.take(10)
        _state.value = _state.value.copy(phoneNumber = filtered)
    }
    
    fun updateAlternatePhoneNumber(value: String) {
        val filtered = value.filter { it.isDigit() }.take(10)
        _state.value = _state.value.copy(alternatePhoneNumber = filtered)
    }
    
    fun updateProfileImage(uri: String) {
        _state.value = _state.value.copy(profileImageUri = uri)
    }
    
    fun removeProfileImage() {
        _state.value = _state.value.copy(profileImageUri = "")
    }
    
    fun updateContactInformation() {
        // Validate required fields
        if (_state.value.email.isBlank() || 
            _state.value.phoneNumber.isBlank() ||
            _state.value.address.isBlank()) {
            _contactResult.value = ContactInformationResult.Error("Please fill in all required fields")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating contact information...")
        
        // Simulate network call
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Contact information updated successfully"
        )
        
        _contactResult.value = ContactInformationResult.Success("Contact information updated")
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _contactResult.value = ContactInformationResult.Idle
    }
    
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".")
    }
}
