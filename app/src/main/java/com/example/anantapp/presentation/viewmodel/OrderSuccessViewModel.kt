package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.anantapp.data.model.OrderSuccessFormState

class OrderSuccessViewModel : ViewModel() {
    private val _formState = MutableStateFlow(OrderSuccessFormState())
    val formState: StateFlow<OrderSuccessFormState> = _formState

    fun updateDeliveryAddress(address: String) {
        _formState.value = _formState.value.copy(deliveryAddress = address)
    }

    fun updateEstimatedDelivery(days: String) {
        _formState.value = _formState.value.copy(estimatedDelivery = days)
    }

    fun updateQRStickerId(id: String) {
        _formState.value = _formState.value.copy(qrStickerId = id)
    }

    fun updateOrderDate(date: String) {
        _formState.value = _formState.value.copy(orderDate = date)
    }

    fun getFormData(): OrderSuccessFormState {
        return _formState.value
    }

    fun resetForm() {
        _formState.value = OrderSuccessFormState()
    }
}
