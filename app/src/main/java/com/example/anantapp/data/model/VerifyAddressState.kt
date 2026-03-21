package com.example.anantapp.data.model

sealed class VerifyAddressResult {
    object Loading : VerifyAddressResult()
    data class Success(val message: String) : VerifyAddressResult()
    data class Error(val error: String) : VerifyAddressResult()
}

data class VerifyAddressState(
    val homeAddress: String = "",
    val houseFlatNumber: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    
    // Validation flags
    val isHomeAddressValid: Boolean = true,
    val isHouseFlatNumberValid: Boolean = true,
    val isAddressValid: Boolean = true,
    val isCityValid: Boolean = true,
    val isStateValid: Boolean = true,
    val isPincodeValid: Boolean = true,
    
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val title: String = "Verify Address"
) {
    val isSubmitEnabled: Boolean
        get() = homeAddress.isNotEmpty() && houseFlatNumber.isNotEmpty() &&
                address.isNotEmpty() && city.isNotEmpty() &&
                state.isNotEmpty() && pincode.length == 6 &&
                isHomeAddressValid && isHouseFlatNumberValid &&
                isAddressValid && isCityValid && isStateValid && isPincodeValid
}
