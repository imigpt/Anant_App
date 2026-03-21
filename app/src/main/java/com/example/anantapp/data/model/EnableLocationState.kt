package com.example.anantapp.data.model

sealed class EnableLocationResult {
    object Loading : EnableLocationResult()
    data class Success(val message: String) : EnableLocationResult()
    data class Error(val error: String) : EnableLocationResult()
}

data class EnableLocationState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String = "",
    val isLocationEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val title: String = "Enable Precise Location"
) {
    val isSubmitEnabled: Boolean
        get() = isLocationEnabled && address.isNotBlank() && !isLoading
}
