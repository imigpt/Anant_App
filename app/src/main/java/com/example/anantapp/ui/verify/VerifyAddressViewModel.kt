package com.example.anantapp.ui.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.data.model.VerifyAddressResult
import com.example.anantapp.data.model.VerifyAddressState
import com.example.anantapp.data.repository.VerifyAddressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerifyAddressViewModel : ViewModel() {
    private val repository = VerifyAddressRepository()
    
    private val _uiState = MutableStateFlow(VerifyAddressState())
    val uiState: StateFlow<VerifyAddressState> = _uiState.asStateFlow()

    fun updateField(fieldName: String, value: String) {
        val currentState = _uiState.value
        
        val newState = when (fieldName) {
            "homeAddress" -> currentState.copy(homeAddress = value, isHomeAddressValid = value.length >= 5)
            "houseFlatNumber" -> currentState.copy(houseFlatNumber = value, isHouseFlatNumberValid = value.isNotEmpty())
            "address" -> currentState.copy(address = value, isAddressValid = value.length >= 5)
            "city" -> currentState.copy(city = value, isCityValid = value.length >= 2)
            "state" -> currentState.copy(state = value, isStateValid = value.length >= 2)
            "pincode" -> {
                val pincodeValue = value.filter { it.isDigit() }.take(6)
                currentState.copy(
                    pincode = pincodeValue,
                    isPincodeValid = pincodeValue.length == 6
                )
            }
            else -> currentState
        }
        
        _uiState.value = newState
    }

    fun submitAddressVerification() {
        val currentState = _uiState.value
        
        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)
            
            try {
                val result = repository.submitAddressVerification(
                    homeAddress = currentState.homeAddress,
                    houseFlatNumber = currentState.houseFlatNumber,
                    address = currentState.address,
                    city = currentState.city,
                    state = currentState.state,
                    pincode = currentState.pincode
                )
                
                handleAddressVerifyResult(result)
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }

    private fun handleAddressVerifyResult(result: VerifyAddressResult) {
        val currentState = _uiState.value
        when (result) {
            is VerifyAddressResult.Loading -> {
                _uiState.value = currentState.copy(isLoading = true)
            }
            is VerifyAddressResult.Success -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    successMessage = result.message
                )
            }
            is VerifyAddressResult.Error -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = result.error
                )
            }
        }
    }

    fun clearMessages() {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
