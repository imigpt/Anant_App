package com.example.anantapp.feature.fundraiser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PreviewAndSubmitState(
    val fundraiserTitle: String = "",
    val fundraiserStory: String = "",
    val goalAmount: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class PreviewAndSubmitResult {
    object Idle : PreviewAndSubmitResult()
    object DraftSaved : PreviewAndSubmitResult()
    data class Success(val fundraiserId: String) : PreviewAndSubmitResult()
    data class Error(val message: String) : PreviewAndSubmitResult()
}

class PreviewAndSubmitViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PreviewAndSubmitState())
    val uiState: StateFlow<PreviewAndSubmitState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow<PreviewAndSubmitResult>(PreviewAndSubmitResult.Idle)
    val result: StateFlow<PreviewAndSubmitResult> = _result.asStateFlow()

    fun saveDraft() {
        _result.value = PreviewAndSubmitResult.DraftSaved
    }

    fun submitFundraiser() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Simulate API call
        _result.value = PreviewAndSubmitResult.Success("fundraiser_456")
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}
