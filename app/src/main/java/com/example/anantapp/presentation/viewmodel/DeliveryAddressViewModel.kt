package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.anantapp.presentation.screen.DeliveryAddressFormState

class DeliveryAddressViewModel : ViewModel() {
    private val _formState = MutableStateFlow(DeliveryAddressFormState())
    val formState: StateFlow<DeliveryAddressFormState> = _formState.asStateFlow()

    fun updateDeliveryName(name: String) {
        _formState.value = _formState.value.copy(deliveryName = name)
    }

    fun updateAddressType(type: String) {
        _formState.value = _formState.value.copy(
            addressType = type,
            isHomeSelected = type == "Home",
            isWorkSelected = type == "Work"
        )
    }

    fun updateHouseNumber(number: String) {
        _formState.value = _formState.value.copy(houseNumber = number)
    }

    fun updateBuildingName(name: String) {
        _formState.value = _formState.value.copy(buildingName = name)
    }

    fun updateStreetLocality(street: String) {
        _formState.value = _formState.value.copy(streetLocality = street)
    }

    fun updateCity(city: String) {
        _formState.value = _formState.value.copy(city = city)
    }

    fun updateState(state: String) {
        _formState.value = _formState.value.copy(state = state)
    }

    fun updatePincode(pincode: String) {
        _formState.value = _formState.value.copy(pincode = pincode)
    }

    fun updateMobileNumber(number: String) {
        _formState.value = _formState.value.copy(mobileNumber = number)
    }

    fun updateAlternateMobileNumber(number: String) {
        _formState.value = _formState.value.copy(alternateMobileNumber = number)
    }

    fun resetForm() {
        _formState.value = DeliveryAddressFormState()
    }

    fun getFormData(): DeliveryAddressFormState = _formState.value
}
