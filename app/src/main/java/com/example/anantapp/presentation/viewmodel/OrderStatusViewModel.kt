package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.anantapp.data.model.OrderStatusFormState

class OrderStatusViewModel : ViewModel() {
    private val _formState = MutableStateFlow(OrderStatusFormState())
    val formState: StateFlow<OrderStatusFormState> = _formState

    fun updateOrderId(orderId: String) {
        _formState.value = _formState.value.copy(orderId = orderId)
    }

    fun updateStatus(status: String) {
        _formState.value = _formState.value.copy(status = status)
    }

    fun updateExpectedDelivery(date: String) {
        _formState.value = _formState.value.copy(expectedDelivery = date)
    }

    fun updateOrderDate(date: String) {
        _formState.value = _formState.value.copy(orderDate = date)
    }

    fun updatePaymentStatus(status: String) {
        _formState.value = _formState.value.copy(paymentStatus = status)
    }

    fun updateTrackingUrl(url: String) {
        _formState.value = _formState.value.copy(trackingUrl = url)
    }

    fun updateQRCode(qrCode: String) {
        _formState.value = _formState.value.copy(qrCode = qrCode)
    }

    fun getFormData(): OrderStatusFormState {
        return _formState.value
    }

    fun resetForm() {
        _formState.value = OrderStatusFormState()
    }
}
