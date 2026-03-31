package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OrderStatusUiState(
    val orderId: String = "",
    val qrCodePath: String = "",
    val status: String = "Processing",
    val estimatedDelivery: String = "",
    val deliveryAddress: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class OrderStatusResult {
    object Idle : OrderStatusResult()
    object TrackingStarted : OrderStatusResult()
    object QRDownloaded : OrderStatusResult()
    data class Error(val message: String) : OrderStatusResult()
}

class OrderStatusViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OrderStatusUiState())
    val uiState: StateFlow<OrderStatusUiState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<OrderStatusResult>(OrderStatusResult.Idle)
    val result: StateFlow<OrderStatusResult> = _result.asStateFlow()

    fun trackOrder() {
        _result.value = OrderStatusResult.TrackingStarted
    }

    fun downloadQR() {
        _result.value = OrderStatusResult.QRDownloaded
    }
}
