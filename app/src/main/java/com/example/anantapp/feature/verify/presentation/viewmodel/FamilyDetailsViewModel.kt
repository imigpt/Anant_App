package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FamilyDetailsUiState(
    val isMarried: Boolean = false,
    val spouseName: String = "",
    val spouseAge: String = "",
    val spouseMobile: String = "",
    val hasChildren: Boolean = false,
    val numChildren: Int = 0,
    val childrenNames: List<String> = emptyList(),
    val childrenAges: List<String> = emptyList(),
    val isSingleParent: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

sealed class FamilyDetailsResult {
    object Idle : FamilyDetailsResult()
    data class Success(val message: String) : FamilyDetailsResult()
    data class Error(val message: String) : FamilyDetailsResult()
}

class FamilyDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FamilyDetailsUiState())
    val uiState: StateFlow<FamilyDetailsUiState> = _uiState.asStateFlow()

    fun toggleMarried(isMarried: Boolean) {
        _uiState.value = _uiState.value.copy(isMarried = isMarried)
    }

    fun updateSpouseField(fieldName: String, value: String) {
        _uiState.value = when (fieldName) {
            "name" -> _uiState.value.copy(spouseName = value)
            "age" -> _uiState.value.copy(spouseAge = value)
            "mobile" -> _uiState.value.copy(spouseMobile = value)
            else -> _uiState.value
        }
    }

    fun toggleHasChildren(hasChildren: Boolean) {
        _uiState.value = _uiState.value.copy(hasChildren = hasChildren)
    }

    fun addChild(name: String, age: String) {
        val updatedNames = _uiState.value.childrenNames.toMutableList() + name
        val updatedAges = _uiState.value.childrenAges.toMutableList() + age
        _uiState.value = _uiState.value.copy(
            childrenNames = updatedNames,
            childrenAges = updatedAges,
            numChildren = updatedNames.size
        )
    }

    fun toggleSingleParent(isSingleParent: Boolean) {
        _uiState.value = _uiState.value.copy(isSingleParent = isSingleParent)
    }

    fun submitFamilyDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Simulate API call
            delay(1000)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                successMessage = "Family details submitted successfully"
            )
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            error = null
        )
    }
}
