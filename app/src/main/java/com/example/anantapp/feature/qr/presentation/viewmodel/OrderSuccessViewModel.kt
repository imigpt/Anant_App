package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OrderSuccessUiState(
    val orderId: String = "",
    val deliveryAddress: String = "",
    val estimatedDelivery: String = "",
    val documentPath: String? = null,
    val isLoading: Boolean = false
)

sealed class OrderSuccessResult {
    object Idle : OrderSuccessResult()
    object PDFDownloaded : OrderSuccessResult()
    object StatusViewed : OrderSuccessResult()
}

class OrderSuccessViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OrderSuccessUiState())
    val uiState: StateFlow<OrderSuccessUiState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<OrderSuccessResult>(OrderSuccessResult.Idle)
    val result: StateFlow<OrderSuccessResult> = _result.asStateFlow()

    fun downloadPDF() {
        _result.value = OrderSuccessResult.PDFDownloaded
    }

    fun viewOrderStatus() {
        _result.value = OrderSuccessResult.StatusViewed
    }
}
