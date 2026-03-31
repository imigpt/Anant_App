package com.example.anantapp.feature.qr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QRCodeScannerUiState(
    val scannedQRCode: String? = null,
    val isScanning: Boolean = false,
    val error: String? = null
)

class QRCodeScannerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QRCodeScannerUiState())
    val uiState: StateFlow<QRCodeScannerUiState> = _uiState.asStateFlow()

    fun onQRCodeScanned(qrCode: String) {
        _uiState.value = _uiState.value.copy(scannedQRCode = qrCode)
    }

    fun startScanning() {
        _uiState.value = _uiState.value.copy(isScanning = true)
    }

    fun stopScanning() {
        _uiState.value = _uiState.value.copy(isScanning = false)
    }
}
