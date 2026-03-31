package com.example.anantapp.feature.fundraiser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FundraiserCategory(
    val id: String,
    val name: String
)

data class SelectFundraiserCategoryUiState(
    val categories: List<FundraiserCategory> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class SelectFundraiserCategoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        SelectFundraiserCategoryUiState(
            categories = listOf(
                FundraiserCategory("health", "Health & Medical"),
                FundraiserCategory("education", "Education"),
                FundraiserCategory("emergency", "Emergency Relief"),
                FundraiserCategory("personal", "Personal Cause"),
                FundraiserCategory("community", "Community Project"),
                FundraiserCategory("other", "Other")
            )
        )
    )
    val uiState: StateFlow<SelectFundraiserCategoryUiState> = _uiState.asStateFlow()

    fun selectCategory(categoryId: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = categoryId)
    }
}
