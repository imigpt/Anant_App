package com.example.anantapp.core.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Base ViewModel class providing common functionality for all ViewModels
 * Handles UI state management and event emissions
 */
abstract class BaseViewModel<UiState, UiEvent>(initialState: UiState) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    /**
     * Update the UI state
     */
    protected fun updateState(block: (UiState) -> UiState) {
        _uiState.value = block(_uiState.value)
    }

    /**
     * Get the current UI state
     */
    protected fun getCurrentState(): UiState = _uiState.value

    /**
     * Emit a UI event
     */
    protected suspend fun emitEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }
}
