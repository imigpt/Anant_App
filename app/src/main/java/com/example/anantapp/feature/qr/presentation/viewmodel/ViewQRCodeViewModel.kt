package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ViewQRCodeUiState(
    val qrCodeImagePath: String = "",
    val qrCodeData: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ViewQRCodeResult {
    object Idle : ViewQRCodeResult()
    object Shared : ViewQRCodeResult()
    object Downloaded : ViewQRCodeResult()
    data class Error(val message: String) : ViewQRCodeResult()
}

class ViewQRCodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ViewQRCodeUiState())
    val uiState: StateFlow<ViewQRCodeUiState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<ViewQRCodeResult>(ViewQRCodeResult.Idle)
    val result: StateFlow<ViewQRCodeResult> = _result.asStateFlow()

    fun shareQRCode() {
        _result.value = ViewQRCodeResult.Shared
    }

    fun downloadQRCode() {
        _result.value = ViewQRCodeResult.Downloaded
    }
}
