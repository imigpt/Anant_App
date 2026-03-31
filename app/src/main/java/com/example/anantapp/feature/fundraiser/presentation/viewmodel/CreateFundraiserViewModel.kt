package com.example.anantapp.feature.fundraiser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CreateFundraiserUiState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val beneficiaryName: String = "",
    val beneficiaryRelation: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class CreateFundraiserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateFundraiserUiState())
    val uiState: StateFlow<CreateFundraiserUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun updateBeneficiaryName(name: String) {
        _uiState.value = _uiState.value.copy(beneficiaryName = name)
    }

    fun updateBeneficiaryRelation(relation: String) {
        _uiState.value = _uiState.value.copy(beneficiaryRelation = relation)
    }
}
